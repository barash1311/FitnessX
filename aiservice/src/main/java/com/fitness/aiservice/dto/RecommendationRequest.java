package com.fitness.aiservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationRequest {
    private String activityId;
    private String userId;
    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;
}