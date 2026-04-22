package org.shtiroy.module1.hm07.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integrations.executors")
public class ExecutorsProperties {

    private final RestTemplateProperties productService;

    public ExecutorsProperties(RestTemplateProperties productService) {
        this.productService = productService;
    }

    public RestTemplateProperties getProductService() {
        return productService;
    }
}
