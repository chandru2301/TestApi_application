package com.api.controller;

import com.api.entity.ApiResponseHistory;
import com.api.repository.ApiResponseHistoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "*")
public class ApiHistoryController {

    private final ApiResponseHistoryRepository historyRepository;

    @Autowired
    public ApiHistoryController(ApiResponseHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping
    public ResponseEntity<List<ApiResponseHistory>> getAllHistory() {
        return ResponseEntity.ok(historyRepository.findAll());
    }

    @GetMapping("/endpoint/{endpointId}")
    public ResponseEntity<List<ApiResponseHistory>> getHistoryByEndpoint(@PathVariable Long endpointId) {
        return ResponseEntity.ok(historyRepository.findByEndpointId(endpointId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseHistory> getHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("History not found with id: " + id)));
    }
} 