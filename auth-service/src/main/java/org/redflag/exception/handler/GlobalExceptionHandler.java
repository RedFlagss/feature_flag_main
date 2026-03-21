package org.redflag.exception.handler;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.redflag.exception.HttpStatusAware;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Produces
@Singleton
@Replaces(io.micronaut.http.server.exceptions.HttpStatusHandler.class)
public class GlobalExceptionHandler implements ExceptionHandler<Exception, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof HttpStatusException httpStatusException) {
            status =  httpStatusException.getStatus();
        }

        if (exception instanceof HttpStatusAware aware) {
            status = aware.getStatus();
        }

        log.error("Error [{}] at path [{}]: {}", status.getCode(), request.getPath(), exception.getMessage());

        var response = new CustomErrorResponse(exception.getMessage(), Map.of(
                "timestamp", LocalDateTime.now(),
                "path", request.getPath()
        ));

        return HttpResponse.status(status).body(response);
    }

//    @Override
//    public HttpResponse<?> handle(HttpRequest request, Exception exception) {
//        HttpStatus status = resolveStatus(exception);
//
//        log.error("Unhandled exception [{}]: {} at path [{}]",
//                exception.getClass().getSimpleName(), exception.getMessage(), request.getPath());
//
//        // Используем LinkedHashMap для сохранения порядка полей в JSON
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("message", exception.getMessage());
//        body.put("status", status.getCode());
//        body.put("error", status.getReason());
//        body.put("timestamp", LocalDateTime.now());
//        body.put("path", request.getPath());
//
//        return HttpResponse.status(status).body(body);
//    }
//
//    private HttpStatus resolveStatus(Exception exception) {
//        // 1. Твои кастомные ошибки
//        if (exception instanceof HttpStatusAware aware) {
//            return aware.getStatus();
//        }
//        // 2. Стандартные ошибки Micronaut (те самые, что мы кидаем в сервисе)
//        if (exception instanceof HttpStatusException httpStatusException) {
//            return httpStatusException.getStatus();
//        }
//        // 3. Всё остальное — 500
//        return HttpStatus.INTERNAL_SERVER_ERROR;
//    }
}