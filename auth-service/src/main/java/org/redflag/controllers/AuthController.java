package org.redflag.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

// пока тестовый класс, не смотреть
@Controller("/api/v1/users")
public class AuthController {

    @Get("/me")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public String getMe(Authentication authentication) {
        // В Java атрибуты возвращаются как Map<String, Object>
        // Извлекаем значение и приводим его к Long (bigint)
        Long userId = (Long) authentication.getAttributes().get("id");

        return "Ваш ID в базе данных: " + userId;

    }


    @Get("/my-session")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public String getSessionId(io.micronaut.session.Session session) {
        return session.getId();
    }
}
