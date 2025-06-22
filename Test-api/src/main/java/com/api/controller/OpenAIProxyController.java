package com.api.controller;

import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/proxy")
@CrossOrigin(origins = "*")
public class OpenAIProxyController {

    private final RestTemplate restTemplate;

    @Autowired
    public OpenAIProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/openai/email")
    public ResponseEntity<String> generateEmail(@RequestBody Map<String, String> request) {
        String prompt = request.getOrDefault("prompt", "Write a professional email");
        
        // Prepare headers with correct Content-Type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Create the request body in the format expected by the API
        String requestBody = "{\"prompt\": \"" + prompt.replace("\"", "\\\"") + "\"}";
        
        // Log request details for debugging
        System.out.println("OpenAI Email Proxy - Request Body: " + requestBody);
        System.out.println("OpenAI Email Proxy - Headers: " + headers);
        
        // Create the HTTP entity
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        
        // Make the request to the actual OpenAI service
        ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:8087/api/openai/email",
            HttpMethod.POST,
            httpEntity,
            String.class
        );
        
        // Return the response directly
        return response;
    }
} 