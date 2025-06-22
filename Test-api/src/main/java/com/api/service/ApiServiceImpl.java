package com.api.service;

import com.api.entity.ApiEndpoint;
import com.api.entity.ApiResponseHistory;
import com.api.model.ApiRequest;
import com.api.model.ApiResponse;
import com.api.repository.ApiEndpointRepository;
import com.api.repository.ApiResponseHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiServiceImpl implements ApiService {
    
    private final RestTemplate restTemplate;
    private final ApiResponseHistoryRepository responseHistoryRepository;
    private final ApiEndpointRepository endpointRepository;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public ApiServiceImpl(RestTemplate restTemplate,
                          ApiResponseHistoryRepository responseHistoryRepository, 
                          ApiEndpointRepository endpointRepository,
                          ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.responseHistoryRepository = responseHistoryRepository;
        this.endpointRepository = endpointRepository;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public ApiResponse executeRequest(ApiRequest request) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse = null;
        
        try {
            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            
            // Set Content-Type header if not present - default to application/json
            boolean hasContentType = false;
            
            if (request.getHeaders() != null) {
                request.getHeaders().forEach((key, value) -> {
                    headers.add(key, value);
                });
                
                // Check if Content-Type is already set
                hasContentType = request.getHeaders().containsKey("Content-Type") || 
                                 request.getHeaders().containsKey("content-type");
            }
            
            // Add Content-Type if not present and body exists
            if (!hasContentType && request.getBody() != null && !request.getBody().isEmpty()) {
                // Check if body starts with { or [ to determine if it's JSON
                String body = request.getBody().trim();
                if ((body.startsWith("{") && body.endsWith("}")) || 
                    (body.startsWith("[") && body.endsWith("]"))) {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                } else {
                    headers.setContentType(MediaType.TEXT_PLAIN);
                }
            }
            
            // Log the request details for debugging
            System.out.println("Request URL: " + request.getUrl());
            System.out.println("Request Method: " + request.getMethod());
            System.out.println("Request Headers: " + headers);
            System.out.println("Request Body: " + request.getBody());
            
            // Prepare request entity
            HttpEntity<String> httpEntity = new HttpEntity<>(request.getBody(), headers);
            
            // Execute request
            HttpMethod method = HttpMethod.valueOf(request.getMethod().toUpperCase());
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                request.getUrl(),
                method,
                httpEntity,
                String.class
            );
            
            long endTime = System.currentTimeMillis();
            
            // Convert response headers to Map<String, String>
            Map<String, String> responseHeaders = new HashMap<>();
            responseEntity.getHeaders().forEach((key, values) -> 
                responseHeaders.put(key, values.stream().collect(Collectors.joining(", ")))
            );
            
            // Build response
            apiResponse = ApiResponse.builder()
                .statusCode(responseEntity.getStatusCode().value())
                .headers(responseHeaders)
                .body(responseEntity.getBody())
                .responseTimeMs(endTime - startTime)
                .build();
                
        } catch (HttpStatusCodeException ex) {
            // Handle HTTP errors (4xx, 5xx) and capture response
            long endTime = System.currentTimeMillis();
            
            // Log the error details
            System.err.println("Error Status: " + ex.getStatusCode());
            System.err.println("Error Response: " + ex.getResponseBodyAsString());
            
            Map<String, String> responseHeaders = new HashMap<>();
            ex.getResponseHeaders().forEach((key, values) -> 
                responseHeaders.put(key, values.stream().collect(Collectors.joining(", ")))
            );
            
            apiResponse = ApiResponse.builder()
                .statusCode(ex.getStatusCode().value())
                .headers(responseHeaders)
                .body(ex.getResponseBodyAsString())
                .responseTimeMs(endTime - startTime)
                .build();
                
        } catch (Exception ex) {
            // Handle other exceptions
            long endTime = System.currentTimeMillis();
            
            System.err.println("General Error: " + ex.getMessage());
            ex.printStackTrace();
            
            apiResponse = ApiResponse.builder()
                .statusCode(500)
                .body("Error executing request: " + ex.getMessage())
                .responseTimeMs(endTime - startTime)
                .build();
        }
        
        // Try to save response history
        try {
            // Look for an endpoint with the same URL and method
            String url = request.getUrl();
            String method = request.getMethod();
            
            // Create response history
            ApiResponseHistory history = new ApiResponseHistory();
            history.setStatusCode(apiResponse.getStatusCode());
            history.setResponseTimeMs(apiResponse.getResponseTimeMs());
            history.setResponseBody(apiResponse.getBody());
            
            // Convert headers to JSON
            try {
                if (request.getHeaders() != null) {
                    history.setRequestHeaders(objectMapper.writeValueAsString(request.getHeaders()));
                }
                if (apiResponse.getHeaders() != null) {
                    history.setResponseHeaders(objectMapper.writeValueAsString(apiResponse.getHeaders()));
                }
            } catch (JsonProcessingException e) {
                // Ignore JSON processing errors
            }
            
            // Set request body
            history.setRequestBody(request.getBody());
            
            // Try to find or create an endpoint
            endpointRepository.findAll().stream()
                .filter(e -> e.getUrl().equals(url) && e.getMethod().equals(method))
                .findFirst()
                .ifPresentOrElse(
                    history::setEndpoint,
                    () -> {
                        // Create a new endpoint if none exists
                        ApiEndpoint newEndpoint = new ApiEndpoint();
                        newEndpoint.setName(url);
                        newEndpoint.setUrl(url);
                        newEndpoint.setMethod(method);
                        try {
                            if (request.getHeaders() != null) {
                                newEndpoint.setHeaders(objectMapper.writeValueAsString(request.getHeaders()));
                            }
                            newEndpoint.setRequestBody(request.getBody());
                        } catch (JsonProcessingException e) {
                            // Ignore JSON processing errors
                        }
                        
                        ApiEndpoint savedEndpoint = endpointRepository.save(newEndpoint);
                        history.setEndpoint(savedEndpoint);
                    }
                );
            
            // Save the response history
            responseHistoryRepository.save(history);
        } catch (Exception ex) {
            // Ignore database errors
            System.err.println("Failed to save response history: " + ex.getMessage());
        }
        
        return apiResponse;
    }
} 