package com.api.model;

import java.util.Map;
import lombok.Data;

@Data
public class ApiRequest {
    private String url;
    private String method;
    private Map<String, String> headers;
    private String body;
} 