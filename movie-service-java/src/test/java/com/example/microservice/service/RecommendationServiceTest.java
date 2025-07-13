package com.example.microservice.service;

import com.example.movieservice.model.RecommendationResponse;
import com.example.movieservice.service.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {

    @InjectMocks
    private RecommendationService recommendationService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecommendationsSuccess() {
        RecommendationResponse mockResponse = new RecommendationResponse();
        mockResponse.setUser_id(1);
        mockResponse.setRecommendations(List.of("Inception", "Matrix"));

        ResponseEntity<RecommendationResponse> responseEntity =
                new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(anyString(), any(), eq(RecommendationResponse.class)))
                .thenReturn(responseEntity);

        RecommendationResponse result = recommendationService.getRecommendations(1);

        assertNotNull(result);
        assertEquals(1, result.getUser_id());
        assertEquals(2, result.getRecommendations().size());
        assertEquals("Inception", result.getRecommendations().get(0));
    }

    @Test
    void testGetRecommendationsFailure() {
        when(restTemplate.postForEntity(anyString(), any(), eq(RecommendationResponse.class)))
                .thenThrow(new RuntimeException("Flask server is down"));



        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->recommendationService.getRecommendations(99));

        assertEquals("Flask server is down", runtimeException.getMessage());

    }
}
