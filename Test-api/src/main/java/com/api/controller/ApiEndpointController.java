package com.api.controller;

import com.api.entity.ApiEndpoint;
import com.api.service.ApiEndpointService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/endpoints")
@CrossOrigin(origins = "*")
public class ApiEndpointController {

    private final ApiEndpointService apiEndpointService;

    @Autowired
    public ApiEndpointController(ApiEndpointService apiEndpointService) {
        this.apiEndpointService = apiEndpointService;
    }

    @GetMapping
    public ResponseEntity<List<ApiEndpoint>> getAllEndpoints() {
        return ResponseEntity.ok(apiEndpointService.getAllEndpoints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiEndpoint> getEndpointById(@PathVariable Long id) {
        return ResponseEntity.ok(apiEndpointService.getEndpointById(id));
    }

    @PostMapping
    public ResponseEntity<ApiEndpoint> createEndpoint(@RequestBody ApiEndpoint endpoint) {
        return new ResponseEntity<>(apiEndpointService.saveEndpoint(endpoint), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiEndpoint> updateEndpoint(@PathVariable Long id, @RequestBody ApiEndpoint endpoint) {
        return ResponseEntity.ok(apiEndpointService.updateEndpoint(id, endpoint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEndpoint(@PathVariable Long id) {
        apiEndpointService.deleteEndpoint(id);
        return ResponseEntity.noContent().build();
    }
} 