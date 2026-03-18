package org.redflag.services.sessionServices;
//package org.redflag.services;
//
//import io.micronaut.http.HttpRequest;
//import io.micronaut.security.authentication.Authentication;
//import io.micronaut.security.filters.AuthenticationFetcher;
//import jakarta.inject.Singleton;
//import lombok.RequiredArgsConstructor;
//import org.reactivestreams.Publisher;
//import org.redflag.repositories.SessionRepository;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//
//@Singleton
//@RequiredArgsConstructor
//public class SessionAuthenticationFetcher implements AuthenticationFetcher<HttpRequest<?>> {
//
//    private final SessionRepository sessionRepository;
//
//    @Override
//    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
//        // 1. Достаем ID сессии из куки
//        return Mono.justOrEmpty(request.getCookies().get("SESSION", String.class))
//                .flatMap(sessionIdStr -> {
//                    Long sessionId = Long.valueOf(sessionIdStr);
//
//                    // 2. Ищем сессию в вашей таблице через Hibernate
//                    return Mono.justOrEmpty(sessionRepository.findById(sessionId));
//                })
//                .filter(session -> session.getTtl().isAfter(LocalDateTime.now())) // Проверка TTL
//                .map(session -> Authentication.build(
//                        session.getUser().getLogin(),
//                        Map.of("id", session.getUser().getId()) // Ваш заветный bigint
//                ));
//    }
//}

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.redflag.entities.Session;
import reactor.core.publisher.Mono;

import java.util.Map;

@Singleton
@RequiredArgsConstructor
public class SessionAuthenticationFetcher implements AuthenticationFetcher<HttpRequest<?>> {

    private final SessionService sessionService;

    @Override
    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
        return Mono.justOrEmpty(extractId(request))
                .flatMap(sessionService::findActiveSession)
                .map(this::mapToAuth);
    }

    private Long extractId(HttpRequest<?> request) {
        return request.getCookies().get("SESSION", String.class).map(Long::valueOf).orElse(null);
    }

    private Authentication mapToAuth(Session session) {
        return Authentication.build(session.getUser().getLogin(), Map.of("id", session.getUser().getId()));
    }
}