package org.redflag.exception;

import io.micronaut.http.HttpStatus;

// 403
public class SessionLimitExceededException extends RuntimeException implements HttpStatusAware {
    public SessionLimitExceededException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
