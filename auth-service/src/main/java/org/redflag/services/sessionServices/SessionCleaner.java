package org.redflag.services.sessionServices;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.redflag.repositories.SessionRepository;

import java.time.LocalDateTime;

@Singleton
@AllArgsConstructor
public class SessionCleaner {

    private static final String CLEANUP_INTERVAL = "${redflag.security.session.cleanup-interval:1h}";

    private final SessionRepository sessionRepository;

    @Scheduled(fixedDelay = CLEANUP_INTERVAL)
//    @Transactional
    public void cleanExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();
        sessionRepository.deleteByTtlBefore(now);
    }
}