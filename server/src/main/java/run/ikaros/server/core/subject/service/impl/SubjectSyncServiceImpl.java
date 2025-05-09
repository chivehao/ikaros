package run.ikaros.server.core.subject.service.impl;

import static run.ikaros.api.core.attachment.AttachmentConst.COVER_DIRECTORY_ID;
import static run.ikaros.api.infra.utils.ReactiveBeanUtils.copyProperties;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.ikaros.api.core.attachment.Attachment;
import run.ikaros.api.core.attachment.AttachmentUploadCondition;
import run.ikaros.api.core.character.Character;
import run.ikaros.api.core.person.Person;
import run.ikaros.api.core.subject.SubjectSync;
import run.ikaros.api.core.subject.SubjectSynchronizer;
import run.ikaros.api.infra.exception.subject.NoAvailableSubjectPlatformSynchronizerException;
import run.ikaros.api.infra.utils.FileUtils;
import run.ikaros.api.store.enums.AttachmentReferenceType;
import run.ikaros.api.store.enums.AttachmentType;
import run.ikaros.api.store.enums.SubjectSyncPlatform;
import run.ikaros.api.store.enums.TagType;
import run.ikaros.server.core.attachment.service.AttachmentService;
import run.ikaros.server.core.subject.service.SubjectService;
import run.ikaros.server.core.subject.service.SubjectSyncService;
import run.ikaros.server.plugin.ExtensionComponentsFinder;
import run.ikaros.server.store.entity.AttachmentReferenceEntity;
import run.ikaros.server.store.entity.BaseEntity;
import run.ikaros.server.store.entity.CharacterEntity;
import run.ikaros.server.store.entity.EpisodeEntity;
import run.ikaros.server.store.entity.PersonEntity;
import run.ikaros.server.store.entity.SubjectCharacterEntity;
import run.ikaros.server.store.entity.SubjectEntity;
import run.ikaros.server.store.entity.SubjectPersonEntity;
import run.ikaros.server.store.entity.SubjectSyncEntity;
import run.ikaros.server.store.entity.TagEntity;
import run.ikaros.server.store.repository.AttachmentReferenceRepository;
import run.ikaros.server.store.repository.AttachmentRepository;
import run.ikaros.server.store.repository.CharacterRepository;
import run.ikaros.server.store.repository.EpisodeRepository;
import run.ikaros.server.store.repository.PersonRepository;
import run.ikaros.server.store.repository.SubjectCharacterRepository;
import run.ikaros.server.store.repository.SubjectPersonRepository;
import run.ikaros.server.store.repository.SubjectRepository;
import run.ikaros.server.store.repository.SubjectSyncRepository;
import run.ikaros.server.store.repository.TagRepository;

@Slf4j
@Service
public class SubjectSyncServiceImpl implements SubjectSyncService,
    ApplicationContextAware {
    private final ExtensionComponentsFinder extensionComponentsFinder;
    private final SubjectService subjectService;
    private final SubjectRepository subjectRepository;
    private final EpisodeRepository episodeRepository;
    private final TagRepository tagRepository;
    private final CharacterRepository characterRepository;
    private final SubjectCharacterRepository subjectCharacterRepository;
    private final PersonRepository personRepository;
    private final SubjectPersonRepository subjectPersonRepository;
    private final AttachmentRepository attachmentRepository;
    private ApplicationContext applicationContext;
    private final SubjectSyncRepository subjectSyncRepository;
    private final AttachmentReferenceRepository attachmentReferenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AttachmentService attachmentService;

    /**
     * Construct.
     */
    public SubjectSyncServiceImpl(ExtensionComponentsFinder extensionComponentsFinder,
                                  SubjectService subjectService,
                                  SubjectSyncRepository subjectSyncRepository,
                                  SubjectRepository subjectRepository,
                                  EpisodeRepository episodeRepository, TagRepository tagRepository,
                                  CharacterRepository characterRepository,
                                  SubjectCharacterRepository subjectCharacterRepository,
                                  PersonRepository personRepository,
                                  SubjectPersonRepository subjectPersonRepository,
                                  AttachmentReferenceRepository attachmentReferenceRepository,
                                  ApplicationEventPublisher applicationEventPublisher,
                                  AttachmentService attachmentService,
                                  AttachmentRepository attachmentRepository) {
        this.extensionComponentsFinder = extensionComponentsFinder;
        this.subjectService = subjectService;
        this.subjectSyncRepository = subjectSyncRepository;
        this.subjectRepository = subjectRepository;
        this.episodeRepository = episodeRepository;
        this.tagRepository = tagRepository;
        this.characterRepository = characterRepository;
        this.subjectCharacterRepository = subjectCharacterRepository;
        this.personRepository = personRepository;
        this.subjectPersonRepository = subjectPersonRepository;
        this.attachmentReferenceRepository = attachmentReferenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.attachmentService = attachmentService;
        this.attachmentRepository = attachmentRepository;
    }

    class SyncTargetExistsException extends RuntimeException {
    }

    @Override
    public Mono<Void> sync(@Nullable Long subjectId, SubjectSyncPlatform platform,
                           String platformId) {
        Assert.notNull(platform, "'platform' must not null.");
        Assert.hasText(platformId, "'platformId' must has text.");
        Assert.isTrue(Long.parseLong(platformId) > 0, "'platformId' must gt 0.");

        final SubjectSynchronizer synchronizer = getSubjectSynchronizerWithPlatform(platform);

        Mono<Boolean> findExistsMono;
        if (subjectId == null) {
            findExistsMono =
                subjectSyncRepository.existsByPlatformAndPlatformId(platform, platformId);
        } else {
            findExistsMono = subjectSyncRepository
                .existsBySubjectIdAndPlatformAndPlatformId(subjectId, platform, platformId);
        }

        // 查询是否已经同步过，同步过则直接返回
        Mono<Boolean> existsMono = findExistsMono.flatMap(exists -> {
            // id为空时操作为PULL，并且已经存在了，则直接退出，不执行下方流程
            if (exists && subjectId == null) {
                return Mono.error(new SyncTargetExistsException());
            }
            // id为空时操作为PULL，但是不存在
            // id不为空时操作为MERGE，存在则更新，不存在则新增
            return Mono.just(exists);
        });

        // 保存条目信息获取ID
        final AtomicReference<Long> subjectIdA =
            new AtomicReference<>(subjectId == null ? -1 : subjectId);
        Mono<SubjectEntity> subjectEntityMono =
            existsMono.then(Mono.just(synchronizer))
                .map(synchronizer1 -> synchronizer1.fetchSubjectWithPlatformId(platformId))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new SyncTargetExistsException()))
                .map(subject -> {
                    log.debug("fetch  subject {} from platform-id: {}-{}",
                        subject, platform, platformId);
                    subjectIdA.set(subject.getId());
                    return subject;
                })
                .flatMap(subject -> {
                    if (subjectId == null) {
                        return Mono.just(new SubjectEntity())
                            .flatMap(entity -> copyProperties(subject, entity, "id"));
                    } else {
                        return subjectRepository.findById(subjectId)
                            .flatMap(entity -> copyProperties(subject, entity, "id"));
                    }
                })
                .map(entity -> {
                    if (entity.getCreateTime() == null) {
                        entity.setCreateTime(LocalDateTime.now());
                    }
                    if (entity.getUpdateTime() == null) {
                        entity.setUpdateTime(LocalDateTime.now());
                    }
                    return entity;
                })
                .flatMap(subjectRepository::save)
                // .flatMap(this::downloadCoverAndSaveRef)
                .map(entity -> {
                    subjectIdA.set(entity.getId());
                    return entity;
                });

        // 保存三方同步信息
        Mono<SubjectSyncEntity> syncEntityMono =
            subjectEntityMono.map(BaseEntity::getId)
                .flatMap(sid -> subjectSyncRepository.findBySubjectIdAndPlatformAndPlatformId(
                        sid, platform, platformId)
                    .switchIfEmpty(Mono.just(SubjectSyncEntity.builder()
                        .syncTime(LocalDateTime.now())
                        .platform(platform)
                        .platformId(platformId)
                        .subjectId(sid)
                        .build())))
                .flatMap(subjectSyncRepository::save);

        // 保存剧集信息
        Mono<List<EpisodeEntity>> episodesMono =
            syncEntityMono.map(subjectSyncEntity -> synchronizer)
                .map(synchronizer1 -> synchronizer1.fetchEpisodesWithPlatformId(platformId))
                .map(episodes -> {
                    if (episodes == null) {
                        episodes = Collections.emptyList();
                    }
                    log.debug("fetch  episodes size {} from platform-id: {}-{}",
                        episodes.size(), platform, platformId);
                    return episodes;
                })
                .flatMapMany(Flux::fromIterable)
                .flatMap(episode -> episodeRepository.findBySubjectIdAndGroupAndSequence(
                        subjectIdA.get(), episode.getGroup(), episode.getSequence())
                    .collectList()
                    .filter(es -> !es.isEmpty())
                    .map(es -> es.get(0))
                    .switchIfEmpty(Mono.just(new EpisodeEntity()))
                    .flatMap(entity -> copyProperties(episode, entity, "id")))
                .map(entity -> {
                    if (entity.getCreateTime() == null) {
                        entity.setCreateTime(LocalDateTime.now());
                    }
                    entity.setSubjectId(subjectIdA.get())
                        .setUpdateTime(LocalDateTime.now());
                    return entity;
                })
                .flatMap(episodeRepository::save)
                .collectList();


        // 保存标签信息
        Mono<List<TagEntity>> tagsMono =
            episodesMono.map(episodeEntities -> synchronizer)
                .map(synchronizer1 -> synchronizer1.fetchTagsWithPlatformId(platformId))
                .map(tags -> {
                    if (tags == null) {
                        tags = Collections.emptyList();
                    }
                    log.debug("fetch  tags {} from platform-id: {}-{}", tags, platform, platformId);
                    return tags;
                })
                .flatMapMany(Flux::fromIterable)
                .flatMap(tag -> tagRepository.findByTypeAndMasterIdAndName(
                        TagType.SUBJECT, subjectIdA.get(), tag.getName())
                    .switchIfEmpty(Mono.just(new TagEntity()))
                    .flatMap(entity -> copyProperties(tag, entity, "id")))
                .map(entity -> entity.setUserId(-1L)
                    .setCreateTime(LocalDateTime.now())
                    .setType(TagType.SUBJECT)
                    .setMasterId(subjectIdA.get()))
                .flatMap(tagRepository::save)
                .collectList();

        // 保存角色信息
        Mono<List<SubjectCharacterEntity>> scMono =
            tagsMono.map(tagEntities -> synchronizer)
                .map(synchronizer1 -> {
                    List<Character> characters =
                        synchronizer1.fetchCharactersWithPlatformId(platformId);
                    log.debug("fetch  characters {} from platform-id: {}-{}", characters, platform,
                        platformId);
                    return characters;
                })
                .filter(Objects::nonNull)
                .flatMapMany(Flux::fromIterable)
                .flatMap(character -> characterRepository.findByName(character.getName())
                    .switchIfEmpty(Mono.just(new CharacterEntity()))
                    .flatMap(entity -> copyProperties(character, entity, "id")))
                .flatMap(characterRepository::save)
                .map(BaseEntity::getId)
                .flatMap(cid -> subjectCharacterRepository.findBySubjectIdAndCharacterId(
                        subjectIdA.get(), cid)
                    .switchIfEmpty(Mono.just(SubjectCharacterEntity.builder()
                        .characterId(cid)
                        .subjectId(subjectIdA.get())
                        .build())))
                .flatMap(subjectCharacterRepository::save)
                .collectList();

        // 保存人物信息
        Mono<List<SubjectPersonEntity>> spMono =
            scMono.map(subjectCharacterEntities -> synchronizer)
                .map(synchronizer1 -> {
                    List<Person> people = synchronizer1.fetchPersonsWithPlatformId(platformId);
                    log.debug("fetch  persons {} from platform-id: {}-{}", people, platform,
                        platformId);
                    return people;
                })
                .filter(Objects::nonNull)
                .flatMapMany(Flux::fromIterable)
                .flatMap(person -> personRepository.findByName(person.getName())
                    .switchIfEmpty(Mono.just(new PersonEntity()))
                    .flatMap(entity -> copyProperties(person, entity, "id")))
                .flatMap(personRepository::save)
                .map(BaseEntity::getId)
                .flatMap(pid -> subjectPersonRepository.findBySubjectIdAndPersonId(
                    subjectIdA.get(), pid
                ).switchIfEmpty(Mono.just(SubjectPersonEntity.builder()
                    .personId(pid)
                    .subjectId(subjectIdA.get())
                    .build())))
                .flatMap(subjectPersonRepository::save)
                .collectList();

        return spMono.map(subjectPersonEntities -> subjectIdA.get())
            .flatMap(subjectRepository::findById)
            .flatMap(this::downloadCoverAndSaveRef)
            .then()

            .onErrorResume(SyncTargetExistsException.class, e -> Mono.empty());
    }

    private Mono<SubjectEntity> downloadCoverAndSaveRef(SubjectEntity entity) {
        final String url = entity.getCover();
        if (StringUtils.isBlank(url) || !url.startsWith("http")) {
            return Mono.just(entity);
        }
        byte[] bytes;
        try {
            bytes = restTemplate.getForObject(url, byte[].class);
        } catch (Exception e) {
            log.warn("down cover fail for subject:{}", entity);
            return Mono.just(entity);
        }
        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
        String coverFileName = StringUtils.isNotBlank(entity.getNameCn())
            ? entity.getNameCn() : entity.getName();
        coverFileName =
            System.currentTimeMillis() + "-" + coverFileName
                + "." + FileUtils.parseFilePostfix(FileUtils.parseFileName(url));
        return attachmentRepository.findByTypeAndParentIdAndName(
                AttachmentType.File, COVER_DIRECTORY_ID, coverFileName
            ).flatMap(attEnt -> copyProperties(attEnt, new Attachment()))
            .switchIfEmpty(attachmentService.upload(AttachmentUploadCondition.builder()
                .parentId(COVER_DIRECTORY_ID)
                .name(coverFileName)
                .dataBufferFlux(Mono.just(dataBufferFactory.wrap(bytes)).flux())
                .build()))
            .flatMap(attachment -> saveCoverAndAttRef(attachment, entity));
    }

    private Mono<SubjectEntity> saveCoverAndAttRef(Attachment attachment, SubjectEntity entity) {
        entity.setCover(attachment.getUrl());
        return attachmentReferenceRepository.findByTypeAndAttachmentIdAndReferenceId(
                AttachmentReferenceType.SUBJECT, attachment.getId(), entity.getId())
            .switchIfEmpty(Mono.just(AttachmentReferenceEntity.builder()
                .type(AttachmentReferenceType.SUBJECT)
                .attachmentId(attachment.getId())
                .referenceId(entity.getId())
                .build()))
            .flatMap(attachmentReferenceRepository::save)
            .flatMap(attRefEn -> subjectRepository.save(entity));
    }

    @Override
    public Mono<SubjectSync> save(SubjectSync subjectSync) {
        log.debug("save: {}", subjectSync);
        return subjectSyncRepository.findBySubjectIdAndPlatformAndPlatformId(
                subjectSync.getSubjectId(), subjectSync.getPlatform(), subjectSync.getPlatformId())
            .switchIfEmpty(Mono.just(new SubjectSyncEntity()
                    .setSubjectId(subjectSync.getSubjectId())
                    .setPlatform(subjectSync.getPlatform())
                    .setPlatformId(subjectSync.getPlatformId())
                    .setSyncTime(subjectSync.getSyncTime()))
                .doOnSuccess(e -> log.debug("create new subject sync record: [{}].", e)))
            .map(entity -> entity.setSubjectId(subjectSync.getSubjectId())
                .setPlatform(subjectSync.getPlatform())
                .setPlatformId(subjectSync.getPlatformId())
                .setSyncTime(subjectSync.getSyncTime()))
            .flatMap(subjectSyncRepository::save)
            .map(entity -> subjectSync
                .setSubjectId(entity.getSubjectId())
                .setPlatform(entity.getPlatform())
                .setPlatformId(entity.getPlatformId())
                .setSyncTime(entity.getSyncTime()));
    }

    @Override
    public Mono<Void> remove(SubjectSync subjectSync) {
        return copyProperties(subjectSync, SubjectSyncEntity.builder().build())
            .flatMap(subjectSyncRepository::delete);
    }

    @Override
    public Flux<SubjectSync> findSubjectSyncsBySubjectId(long subjectId) {
        Assert.isTrue(subjectId > 0, "'subjectId' must gt 0.");
        return subjectSyncRepository.findAllBySubjectId(subjectId)
            .flatMap(subjectSyncEntity -> copyProperties(subjectSyncEntity,
                SubjectSync.builder().build()));
    }

    @Override
    public Mono<SubjectSync> findSubjectSyncBySubjectIdAndPlatform(long subjectId,
                                                                   SubjectSyncPlatform platform) {
        Assert.isTrue(subjectId > 0, "'subjectId' must gt 0.");
        Assert.notNull(platform, "'platform' must not null.");
        return subjectSyncRepository.findBySubjectIdAndPlatform(subjectId, platform)
            .flatMap(subjectSyncEntity -> copyProperties(subjectSyncEntity,
                SubjectSync.builder().build()));
    }

    @Override
    public Flux<SubjectSync> findSubjectSyncsByPlatformAndPlatformId(SubjectSyncPlatform platform,
                                                                     String platformId) {
        Assert.notNull(platform, "'platform' must not null.");
        Assert.hasText(platformId, "'platformId' must has text.");
        return subjectSyncRepository.findByPlatformAndPlatformId(platform, platformId)
            .flatMap(subjectSyncEntity -> copyProperties(subjectSyncEntity,
                SubjectSync.builder().build()));
    }

    @Override
    public Mono<SubjectSync> findBySubjectIdAndPlatformAndPlatformId(Long subjectId,
                                                                     SubjectSyncPlatform platform,
                                                                     String platformId) {
        Assert.isTrue(subjectId > 0, "'subjectId' must gt 0.");
        Assert.notNull(platform, "'platform' must not null.");
        Assert.hasText(platformId, "'platformId' must has text.");
        return subjectSyncRepository.findBySubjectIdAndPlatformAndPlatformId(
                subjectId, platform, platformId)
            .flatMap(subjectSyncEntity -> copyProperties(subjectSyncEntity,
                SubjectSync.builder().build()));
    }


    private SubjectSynchronizer getSubjectSynchronizerWithPlatform(SubjectSyncPlatform platform) {
        return extensionComponentsFinder.getExtensions(SubjectSynchronizer.class)
            .stream()
            .filter(synchronizer -> platform.equals(synchronizer.getSyncPlatform()))
            .findFirst()
            .orElseThrow(() -> new NoAvailableSubjectPlatformSynchronizerException(
                "No found available subject platform synchronizer for platform: "
                    + platform.name()
            ));
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
    }
}
