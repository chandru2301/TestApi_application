package com.api.service;

import com.api.model.ApiRequest;
import com.api.model.ApiResponse;

public interface ApiService {
    ApiResponse executeRequest(ApiRequest request);
} 