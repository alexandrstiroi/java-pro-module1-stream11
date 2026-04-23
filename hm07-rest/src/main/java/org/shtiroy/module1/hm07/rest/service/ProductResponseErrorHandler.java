package org.shtiroy.module1.hm07.rest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.shtiroy.module1.hm07.rest.dto.RemoteApiErrorResponse;
import org.shtiroy.module1.hm07.rest.exception.RemoteServiceException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public ProductResponseErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus status = HttpStatus.valueOf(response.getStatusCode().value());
        String responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        throw new RemoteServiceException(status, parseError(responseBody, status));
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        handleError(response);
    }

    private RemoteApiErrorResponse parseError(String body, HttpStatus status) {
        try {
            return objectMapper.readValue(body, RemoteApiErrorResponse.class);
        } catch (JsonProcessingException exception) {
            return new RemoteApiErrorResponse(
                    LocalDateTime.now(),
                    status.value(),
                    status.getReasonPhrase(),
                    "product-service",
                    List.of(body == null || body.isBlank() ? "Product service error" : body)
            );
        }
    }
}
