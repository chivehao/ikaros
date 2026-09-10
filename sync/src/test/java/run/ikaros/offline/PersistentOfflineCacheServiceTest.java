package run.ikaros.offline;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import run.ikaros.sync.api.DeviceTrustQuery;

class PersistentOfflineCacheServiceTest {
    @Test
    void evictsCacheButProtectsExplicitDownload() {
        UUID user = UUID.randomUUID(); UUID device = UUID.randomUUID(); UUID resource = UUID.randomUUID();
        UUID protectedAttachment = UUID.randomUUID(); UUID cacheAttachment = UUID.randomUUID(); Instant now = Instant.now();
        OfflineCacheEntryEntity protectedEntry = entry(user, device, resource, protectedAttachment, 10L, now);
        OfflineCacheEntryEntity evictableEntry = entry(user, device, resource, cacheAttachment, 20L, now);
        DownloadIntentEntity download = new DownloadIntentEntity(UUID.randomUUID(), user, device, resource,
            protectedAttachment, OfflineCopyKind.DOWNLOAD, DownloadState.COMPLETED, null, 1, now, now, 0L);
        OfflineCacheEntryRepository entries = org.mockito.Mockito.mock(OfflineCacheEntryRepository.class);
        DownloadIntentRepository downloads = org.mockito.Mockito.mock(DownloadIntentRepository.class);
        DeviceTrustQuery devices = org.mockito.Mockito.mock(DeviceTrustQuery.class);
        when(devices.isUsable(user, device)).thenReturn(Mono.just(true));
        when(downloads.findAllByUserIdAndDeviceIdOrderByCreatedAtDesc(user, device)).thenReturn(Flux.just(download));
        when(entries.findAllByUserIdAndDeviceIdOrderByLastAccessedAtDesc(user, device)).thenReturn(Flux.just(protectedEntry, evictableEntry));
        when(entries.save(any(OfflineCacheEntryEntity.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(new PersistentOfflineCacheService(entries, downloads, devices).evictEligible(user, device))
            .assertNext(result -> { org.assertj.core.api.Assertions.assertThat(result.evictedCount()).isEqualTo(1); org.assertj.core.api.Assertions.assertThat(result.evictedBytes()).isEqualTo(20L); org.assertj.core.api.Assertions.assertThat(result.protectedDownloadCount()).isEqualTo(1); })
            .verifyComplete();
        verify(entries).save(org.mockito.ArgumentMatchers.argThat(value -> value.id().equals(evictableEntry.id()) && value.state() == CacheEntryState.EVICTED));
    }

    @Test
    void rejectsRevokedDeviceBeforeReadingCache() {
        UUID user = UUID.randomUUID(); UUID device = UUID.randomUUID();
        OfflineCacheEntryRepository entries = org.mockito.Mockito.mock(OfflineCacheEntryRepository.class);
        DownloadIntentRepository downloads = org.mockito.Mockito.mock(DownloadIntentRepository.class);
        DeviceTrustQuery devices = org.mockito.Mockito.mock(DeviceTrustQuery.class);
        when(devices.isUsable(user, device)).thenReturn(Mono.just(false));

        StepVerifier.create(new PersistentOfflineCacheService(entries, downloads, devices).evictEligible(user, device))
            .expectErrorMatches(error -> error instanceof run.ikaros.common.ConflictException)
            .verify();
    }

    private OfflineCacheEntryEntity entry(UUID user, UUID device, UUID resource, UUID attachment, long size, Instant now) {
        return new OfflineCacheEntryEntity(UUID.randomUUID(), user, device, resource, attachment, size, "hash",
            CacheEntryState.ACTIVE, now, now, now, 0L);
    }
}
