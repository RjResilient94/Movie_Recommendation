package com.example.movieservice.controller;

import com.example.movieservice.model.RecommendationResponse;
import com.example.movieservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public RecommendationResponse getRecommendations(@PathVariable int userId) {
        return recommendationService.getRecommendations(userId);
    }
}