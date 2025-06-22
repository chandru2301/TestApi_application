package com.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "api_response_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "endpoint_id")
    private ApiEndpoint endpoint;
    
    @Column(nullable = false)
    private Integer statusCode;
    
    @Lob
    @Column(columnDefinition = "JSON")
    private String requestHeaders;
    
    @Lob
    @Column(columnDefinition = "JSON")
    private String requestBody;
    
    @Lob
    @Column(columnDefinition = "JSON")
    private String responseHeaders;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String responseBody;
    
    @Column
    private Long responseTimeMs;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
} 