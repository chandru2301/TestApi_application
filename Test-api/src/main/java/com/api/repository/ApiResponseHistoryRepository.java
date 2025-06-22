package com.api.repository;

import com.api.entity.ApiResponseHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiResponseHistoryRepository extends JpaRepository<ApiResponseHistory, Long> {
    List<ApiResponseHistory> findByEndpointId(Long endpointId);
} 