package run.ikaros.server.core.attachment.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.ikaros.api.core.attachment.AttachmentReference;
import run.ikaros.api.store.enums.AttachmentReferenceType;
import run.ikaros.api.store.enums.EpisodeGroup;

public interface AttachmentReferenceService {
    Mono<AttachmentReference> save(AttachmentReference attachmentReference);

    Flux<AttachmentReference> findAllByTypeAndAttachmentId(
        AttachmentReferenceType type, Long attachmentId);

    Mono<Void> removeById(Long attachmentRefId);

    Mono<Void> removeAllByTypeAndReferenceId(AttachmentReferenceType type, Long referenceId);

    Mono<Void> removeByTypeAndAttachmentIdAndReferenceId(
        AttachmentReferenceType type, Long attachmentId, Long referenceId);

    Mono<Void> matchingAttachmentsAndSubjectEpisodes(Long subjectId, Long[] attachmentIds);

    Mono<Void> matchingAttachmentsAndSubjectEpisodes(Long subjectId, Long[] attachmentIds,
                                                     EpisodeGroup group);

    Mono<Void> matchingAttachmentsAndSubjectEpisodes(Long subjectId, Long[] attachmentIds,
                                                     boolean notify);

    Mono<Void> matchingAttachmentsAndSubjectEpisodes(Long subjectId, Long[] attachmentIds,
                                                     EpisodeGroup group, boolean notify);

    Mono<Void> matchingAttachmentsForEpisode(Long episodeId, Long[] attachmentIds);

}
