package run.ikaros.server.core.migration;

import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import run.ikaros.api.infra.utils.UuidV7Utils;

@Slf4j
@Component
@ConditionalOnProperty(name = "ikaros.migration.enable", havingValue = "true")
public class MigrationInitializer {
    private final MigrationProperties migrationProperties;
    private final R2dbcEntityTemplate template;

    public MigrationInitializer(MigrationProperties migrationProperties,
                                R2dbcEntityTemplate template) {
        this.migrationProperties = migrationProperties;
        this.template = template;
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
            })
            .flatMapSequential(tableName -> {
                if ("flyway_schema_history".equalsIgnoreCase(tableName)) {
                    return Mono.empty();
                }
                return template.getDatabaseClient()
                    .sql("select id from " + tableName + ";")
                    .fetch()
                    .all()
                    .index()
                    .map(tuple2 -> tuple2.getT2().get("id"))
                    .collectList()
                    .map(ids -> {
                        Map<String, String> idUuidMap = new HashMap<>(ids.size());
                        for (Object id : ids) {
                            idUuidMap.putIfAbsent(String.valueOf(id), UuidV7Utils.generate());
                        }
                        return idUuidMap;
                    })
                    .map(idUuidMap -> Tuples.of(tableName, idUuidMap));
            })
            .collectList()
            .map(tuple2s -> {
                Map<String, Map<String, String>> nameIdUuidMaps = new HashMap<>(tuple2s.size());
                for (Tuple2<String, Map<String, String>> tuple2 : tuple2s) {
                    nameIdUuidMaps.putIfAbsent(tuple2.getT1(), tuple2.getT2());
                }
                return nameIdUuidMaps;
            })
            .flatMap(nameIdUuidMap ->
                migrationWithNameIdUuidMap(targetClient, nameIdUuidMap));
    }

    private Mono<Void> updateUuidColumnForAllTable() {
        return Mono.empty();
    }

    private Mono<Void> migrationWithNameIdUuidMap(
        DatabaseClient targetClient, Map<String, Map<String, String>> nameIdUuidMaps) {


        return Mono.empty();
    }
}
