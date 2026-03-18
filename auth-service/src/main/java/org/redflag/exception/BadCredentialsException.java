package org.redflag.exception;

import io.micronaut.http.HttpStatus;

/**
 * Ошибка: Неверный логин или пароль.
 * Статус: 401 Unauthorized (Пользователь не узнан)
 */
public class BadCredentialsException extends RuntimeException implements HttpStatusAware {

    public BadCredentialsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        // Для ошибок входа стандарт — 401
        return HttpStatus.UNAUTHORIZED;
    }
}