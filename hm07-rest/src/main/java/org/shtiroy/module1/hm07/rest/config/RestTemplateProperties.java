package org.shtiroy.module1.hm07.rest.config;

import java.time.Duration;

public record RestTemplateProperties(String url, Duration connectTimeout, Duration readTimeout) {

}
