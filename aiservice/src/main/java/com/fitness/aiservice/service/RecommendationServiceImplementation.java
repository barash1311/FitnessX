package com.fitness.aiservice.service;

import com.fitness.aiservice.dto.RecommendationResponse;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImplementation implements RecommendationService{
    private final RecommendationRepository recommendationRepository;

    @Override
    public List<RecommendationResponse> getUserRecommendation(String userId) {
        try {
            UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid userId format. Expected UUID: " + userId);
        }
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public RecommendationResponse getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId).orElseThrow(
                ()-> new RuntimeException("No recommendation found for activityId: "+activityId)
        );
    }
}
