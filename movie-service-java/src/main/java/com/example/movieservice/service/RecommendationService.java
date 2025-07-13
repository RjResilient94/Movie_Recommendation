package com.example.movieservice.service;

import com.example.movieservice.model.RecommendationResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate;

    public RecommendationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RecommendationResponse getRecommendations(int userId) {
        String url = "http://localhost:5000/predict";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{\"user_id\":" + userId + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        try {
            ResponseEntity<RecommendationResponse> response =
                    restTemplate.postForEntity(url, entity, RecommendationResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                RecommendationResponse fallback = new RecommendationResponse();
                fallback.setUser_id(userId);
                fallback.setRecommendations(List.of("No valid recommendations returned."));
                return fallback;
            }

        } catch (RestClientException e) {
            System.err.println("Failed to call Flask API: " + e.getMessage());
            RecommendationResponse errorResponse = new RecommendationResponse();
            errorResponse.setUser_id(userId);
            errorResponse.setRecommendations(List.of("Error connecting to ML service."));
            return errorResponse;
        }
    }
}
