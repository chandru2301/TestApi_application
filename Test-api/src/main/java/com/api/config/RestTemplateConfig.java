package com.api.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        
        // Add support for converting different content types
        configureMessageConverters(restTemplate);
        
        // Add logging interceptor if needed
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        // Add custom interceptors here if needed
        restTemplate.setInterceptors(interceptors);
        
        return restTemplate;
    }
    
    private void configureMessageConverters(RestTemplate restTemplate) {
        // Configure StringHttpMessageConverter to handle text content
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConverter.setSupportedMediaTypes(List.of(
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_HTML,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_OCTET_STREAM,
            MediaType.ALL
        ));
        
        // Configure Jackson converter to handle JSON content with various content types
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(List.of(
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_OCTET_STREAM,
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_HTML,
            new MediaType("application", "*+json"),
            MediaType.ALL
        ));
        
        // Get existing converters and add our custom ones
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(0, jacksonConverter);
        messageConverters.add(0, stringConverter);
    }
} 