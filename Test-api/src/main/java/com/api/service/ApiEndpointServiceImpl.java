package com.api.service;

import com.api.entity.ApiEndpoint;
import com.api.repository.ApiEndpointRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiEndpointServiceImpl implements ApiEndpointService {

    private final ApiEndpointRepository apiEndpointRepository;

    @Autowired
    public ApiEndpointServiceImpl(ApiEndpointRepository apiEndpointRepository) {
        this.apiEndpointRepository = apiEndpointRepository;
    }

    @Override
    public List<ApiEndpoint> getAllEndpoints() {
        return apiEndpointRepository.findAll();
    }

    @Override
    public ApiEndpoint getEndpointById(Long id) {
        return apiEndpointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("API Endpoint not found with id: " + id));
    }

    @Override
    public ApiEndpoint saveEndpoint(ApiEndpoint endpoint) {
        return apiEndpointRepository.save(endpoint);
    }

    @Override
    public ApiEndpoint updateEndpoint(Long id, ApiEndpoint endpoint) {
        ApiEndpoint existingEndpoint = getEndpointById(id);
        existingEndpoint.setName(endpoint.getName());
        existingEndpoint.setUrl(endpoint.getUrl());
        existingEndpoint.setMethod(endpoint.getMethod());
        existingEndpoint.setHeaders(endpoint.getHeaders());
        existingEndpoint.setRequestBody(endpoint.getRequestBody());
        existingEndpoint.setDescription(endpoint.getDescription());
        return apiEndpointRepository.save(existingEndpoint);
    }

    @Override
    public void deleteEndpoint(Long id) {
        apiEndpointRepository.deleteById(id);
    }
} 