package com.api.controller;

import com.api.model.ApiRequest;
import com.api.model.ApiResponse;
import com.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class ApiTestController {

    private final ApiService apiService;

    @Autowired
    public ApiTestController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping(
        value = "/execute",
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.ALL_VALUE
        }
    )
    public ResponseEntity<ApiResponse> executeApiRequest(@RequestBody ApiRequest request) {
        ApiResponse response = apiService.executeRequest(request);
        return ResponseEntity.ok(response);
    }
} 