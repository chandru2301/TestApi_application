package com.api.service;

import com.api.entity.ApiEndpoint;
import java.util.List;

public interface ApiEndpointService {
    List<ApiEndpoint> getAllEndpoints();
    ApiEndpoint getEndpointById(Long id);
    ApiEndpoint saveEndpoint(ApiEndpoint endpoint);
    ApiEndpoint updateEndpoint(Long id, ApiEndpoint endpoint);
    void deleteEndpoint(Long id);
} 