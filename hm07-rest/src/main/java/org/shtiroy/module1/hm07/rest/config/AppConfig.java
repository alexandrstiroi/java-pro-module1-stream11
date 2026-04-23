package org.shtiroy.module1.hm07.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.shtiroy.module1.hm07.rest.service.ProductResponseErrorHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@EnableConfigurationProperties(ExecutorsProperties.class)
public class AppConfig {

    private final ExecutorsProperties executorsProperties;

    public AppConfig(ExecutorsProperties executorsProperties) {
        this.executorsProperties = executorsProperties;
    }

    @Bean
    public ProductResponseErrorHandler productResponseErrorHandler(ObjectMapper objectMapper) {
        return new ProductResponseErrorHandler(objectMapper);
    }

    @Bean
    public RestTemplate restTemplate(ProductResponseErrorHandler errorHandler) {
        RestTemplateProperties productClientProperties = executorsProperties.getProductService();
        return new RestTemplateBuilder()
                .rootUri(productClientProperties.url())
                .setConnectTimeout(productClientProperties.connectTimeout())
                .setReadTimeout(productClientProperties.readTimeout())
                .errorHandler(errorHandler)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.build();
    }
}
