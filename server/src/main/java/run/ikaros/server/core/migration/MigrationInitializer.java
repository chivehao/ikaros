package run.ikaros.server.core.migration;

import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;
import static run.ikaros.api.core.attachment.AttachmentConst.COVER_DIRECTORY_ID;
import static run.ikaros.api.core.attachment.AttachmentConst.DOWNLOAD_DIRECTORY_ID;
import static run.ikaros.api.core.attachment.AttachmentConst.ROOT_DIRECTORY_ID;
import static run.ikaros.api.core.attachment.AttachmentConst.V_COVER_DIRECTORY_ID;
import static run.ikaros.api.core.attachment.AttachmentConst.V_DOWNLOAD_DIRECTORY_ID;
import static run.ikaros.api.core.attachment.AttachmentConst.V_ROOT_DIRECTORY_UUID;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import jakarta.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import run.ikaros.api.infra.utils.UuidV7Utils;
import run.ikaros.server.infra.utils.JsonUtils;
import run.ikaros.server.store.entity.AttachmentEntity;

@Slf4j
@Component
@ConditionalOnProperty(name = "ikaros.migration.enable", havingValue = "true")
public class MigrationInitializer {
    private final MigrationProperties migrationProperties;
    private final R2dbcEntityTemplate template;
    private final RelationalMappingContext mappingContext;

    private static Map<String, String> rkTableNameMap = new HashMap<>();

    static {
        rkTableNameMap.putAll(Map.of(
            "driver_id", "attachment_driver",
            "user_id", "ikuser",
            "attachment_id", "attachment",
            "reference_id@type@USER_AVATAR", "attachment",
            "reference_id@type@EPISODE", "episode",
            "reference_id@type@SUBJECT", "subject",
            "relation_attachment_id", "attachment",
            "update_uid", "ikuser",
            "create_uid", "ikuser",
            "custom_id", "custom"
        ));
        rkTableNameMap.putAll(Map.of(
            "subject_id", "subject",
            "episode_id", "episode",
            "episode_list_id", "episode_list",
            "role_id", "role",
            "character_id", "character",
            "person_id", "person",
            "authority_id", "authority",
            "relation_subject_id", "subject",
            "master_id", "ikuser"
        ));
    }

    /**
     * Construct.
     */
    public MigrationInitializer(MigrationProperties migrationProperties,
                                R2dbcEntityTemplate template,
                                RelationalMappingContext mappingContext) {
        this.migrationProperties = migrationProperties;
        this.template = template;
        this.mappingContext = mappingContext;
    }

    @EventListener(ApplicationReadyEvent.class)
    private Mono<Void> doMigration(ApplicationReadyEvent event) {
        log.info("Start migration database table records to new database...");
        ConnectionFactoryOptions baseOptions =
            ConnectionFactoryOptions.parse(migrationProperties.getR2dbc().getUrl());

        ConnectionFactoryOptions finalOptions = baseOptions.mutate()
            .option(USER, migrationProperties.getR2dbc().getUsername())
            .option(PASSWORD, migrationProperties.getR2dbc().getPassword())
            .build();

        // Creates a ConnectionPool wrapping an underlying ConnectionFactory
        ConnectionFactory pooledConnectionFactory = ConnectionFactories.get(finalOptions);

        // Create database client
        DatabaseClient targetClient = DatabaseClient.create(pooledConnectionFactory);

        return updateUuidColumnValueForAllTableIfNotExists();
    }

    @Nonnull
    private Flux<String> fetchTableNames() {
        // 查询所有的表ID，每张表生成一个Map<ID, UUID>在内存中，
        // 其中附件表ID=0时设置成AttachmentConst里的RootUUID
        // 最后统合内存中的ID=>UUID为 Map<TabName, Map<ID, UUID>>
        String name = template.getDatabaseClient().getConnectionFactory()
            .getMetadata()
            .getName();
        String sql = "SELECT tablename FROM pg_tables WHERE schemaname = 'public';";
        String tableNameKey;
        if (name.toLowerCase().contains("h2")) {
            sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES "
                + "WHERE TABLE_SCHEMA = 'PUBLIC'";
            tableNameKey = "TABLE_NAME";
        } else {
            tableNameKey = "tablename";
        }
        return template.getDatabaseClient().sql(sql)
            .fetch()
            .all()
            .index()
            .map(t -> {
                Long index = t.getT1();
                Object o = t.getT2().get(tableNameKey);
                return String.valueOf(o);
            });
    }

    @Nonnull
    private Flux<Object> fetchTableIds(String tableName) {
        if ("flyway_schema_history".equalsIgnoreCase(tableName)) {
            return Flux.empty();
        }
        return template.getDatabaseClient()
            .sql("select id from " + tableName + ";")
            .fetch()
            .all()
            .index()
            .map(tuple2 -> tuple2.getT2().get("id"));
    }

    @Nonnull
    private Mono<Map<String, Map<String, String>>> getDbnameIdUuidMap() {
        return fetchTableNames()
            .flatMapSequential(tableName ->
                fetchTableIds(tableName)
                    .collectList()
                    .map(ids -> {
                        Map<String, String> idUuidMap = new HashMap<>(ids.size());
                        for (Object id : ids) {
                            idUuidMap.putIfAbsent(String.valueOf(id), UuidV7Utils.generate());
                        }
                        return idUuidMap;
                    })
                    .map(idUuidMap -> Tuples.of(tableName, idUuidMap)))
            .collectList()
            .map(tuple2s -> {
                Map<String, Map<String, String>> nameIdUuidMaps = new HashMap<>(tuple2s.size());
                for (Tuple2<String, Map<String, String>> tuple2 : tuple2s) {
                    nameIdUuidMaps.putIfAbsent(tuple2.getT1(), tuple2.getT2());
                }
                return nameIdUuidMaps;
            });
    }

    /**
     * 根据表名获取类.
     */
    private Class<?> getEntityClassByTableName(String tableName) {
        return mappingContext.getPersistentEntities().stream()
            .filter(e -> tableName.equalsIgnoreCase(e.getTableName().getReference()))
            .map(PersistentEntity::getType)
            .findFirst()
            .orElseThrow();
    }

    private Mono<Void> updateUuidColumnValueForAllTableIfNotExists() {
        return template.update(AttachmentEntity.class)
            .matching(Query.query(Criteria.empty()
                .and("id").is(ROOT_DIRECTORY_ID)
                .and("uuid").isNull()))
            .apply(Update.update("uuid", V_ROOT_DIRECTORY_UUID))
            .then(template.update(AttachmentEntity.class)
                .matching(Query.query(Criteria.empty()
                    .and("id").is(COVER_DIRECTORY_ID)
                    .and("uuid").isNull()))
                .apply(Update.update("uuid", V_COVER_DIRECTORY_ID)))
            .then(template.update(AttachmentEntity.class)
                .matching(Query.query(Criteria.empty()
                    .and("id").is(DOWNLOAD_DIRECTORY_ID)
                    .and("uuid").isNull()))
                .apply(Update.update("uuid", V_DOWNLOAD_DIRECTORY_ID)))
            .thenMany(Flux.defer(this::fetchTableNames))
            .parallel(4)
            .flatMap(tableName -> {
                return fetchTableIds(tableName)
                    .parallel(10)
                    .flatMap(id -> template.update(getEntityClassByTableName(tableName))
                        .matching(Query.query(Criteria.empty()
                            .and("id").is(id)
                            .and("uuid").isNull()))
                        .apply(Update.update("uuid", UuidV7Utils.generate()))
                        .then())
                    .runOn(Schedulers.boundedElastic());
            })
            .runOn(Schedulers.boundedElastic())
            .then();
    }

    private Mono<Void> migrationWithNameIdUuidMap(
        DatabaseClient targetClient, Map<String, Map<String, String>> nameIdUuidMaps) {
        return fetchTableNames()
            .flatMapSequential(tabName -> fetchTableIds(tabName)
                .flatMapSequential(id -> template.getDatabaseClient()
                    .sql("select * form" + tabName + " where id=:id;")
                    .bind("id", id)
                    .fetch()
                    .one()
                    .map(this::replaceCanModifyMap)
                    .map(this::replaceIdValueFromUuid)
                    .flatMap(this::replaceRkIdValueFromMasterTableIdAndUuid)
                    .flatMap(this::replaceReferenceIdRkIdValueFromMasterTableIdAndUuid)
                    .flatMap(recordMap ->
                        saveRecordToNewDatabase(tabName, recordMap, targetClient))
                )

            ).then();
    }

    private Map<String, Object> replaceCanModifyMap(Map<String, Object> recordMap) {
        Map<String, Object> newMap = new HashMap<>(recordMap.size());
        newMap.putAll(recordMap);
        return newMap;
    }

    private Map<String, Object> replaceIdValueFromUuid(
        Map<String, Object> recordMap
    ) {
        Object uuid = recordMap.get("uuid");
        recordMap.put("id", uuid);
        return recordMap;
    }

    private Mono<Map<String, Object>> replaceRkIdValueFromMasterTableIdAndUuid(
        Map<String, Object> recordMap
    ) {
        return Flux.fromStream(Map.copyOf(recordMap).keySet().stream())
            .filter(name -> rkTableNameMap.containsKey(name))
            .flatMapSequential(name -> template.getDatabaseClient()
                .sql("select uuid form " + rkTableNameMap.get(name) + " where id=:id")
                .bind("id", recordMap.get(name))
                .fetch()
                .one()
                .map(recordMap2 -> recordMap2.get("uuid"))
                .map(uuid -> recordMap.put(name, uuid))
                .then()
            )
            .then(Mono.defer(() -> Mono.just(recordMap)));
    }

    private Mono<Map<String, Object>> replaceReferenceIdRkIdValueFromMasterTableIdAndUuid(
        Map<String, Object> recordMap
    ) {
        Map<String, Object> onlyReadMap = Map.copyOf(recordMap);
        if (!onlyReadMap.containsKey("reference_id")) {
            return Mono.just(recordMap);
        }
        if (!onlyReadMap.containsKey("type")) {
            log.warn("Migration table 'attachment_reference' has issue for record: {}",
                JsonUtils.obj2Json(recordMap));
            return Mono.just(recordMap);
        }
        String rkKey = "reference_id@type@" + onlyReadMap.get("type").toString().toUpperCase();

        return template.getDatabaseClient()
            .sql("select uuid form " + rkTableNameMap.get(rkKey) + " where id=:id")
            .bind("id", recordMap.get("reference_id"))
            .fetch()
            .one()
            .map(recordMap2 -> recordMap2.get("uuid"))
            .map(uuid -> recordMap.put("reference_id", uuid))
            .then(Mono.defer(() -> Mono.just(recordMap)));
    }


    private Mono<Void> saveRecordToNewDatabase(String tabName,
                                               Map<String, Object> recordMap,
                                               DatabaseClient targetClient) {
        recordMap.remove("id");

        return Mono.empty();
    }
}
