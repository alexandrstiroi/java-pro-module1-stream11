package org.shtiroy.module1.hm07.rest.exception;

import org.shtiroy.module1.hm07.rest.dto.RemoteApiErrorResponse;
import org.springframework.http.HttpStatus;

public class RemoteServiceException extends RuntimeException {
    private final HttpStatus status;
    private final RemoteApiErrorResponse errorResponse;

    public RemoteServiceException(HttpStatus status, RemoteApiErrorResponse errorResponse) {
        super(errorResponse.details() != null && !errorResponse.details().isEmpty()
                ? errorResponse.details().get(0)
                : "Remote service error");
        this.status = status;
        this.errorResponse = errorResponse;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public RemoteApiErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
