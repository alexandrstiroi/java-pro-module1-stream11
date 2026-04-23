package org.shtiroy.module1.hm07.rest.controller;

import org.shtiroy.module1.hm07.rest.dto.ApiErrorResponse;
import org.shtiroy.module1.hm07.rest.dto.RemoteApiErrorResponse;
import org.shtiroy.module1.hm07.rest.exception.RemoteServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RemoteServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleRemoteServiceException(RemoteServiceException exception) {
        RemoteApiErrorResponse remote = exception.getErrorResponse();
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                exception.getStatus().value(),
                remote.error(),
                remote.service(),
                remote.details()
        );
        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
