package com.fitness.aiservice.repository;

import com.fitness.aiservice.dto.RecommendationResponse;
import com.fitness.aiservice.models.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation,String> {
    List<RecommendationResponse> findByUserId(String userId);
    Optional<RecommendationResponse> findByActivityId(String activityId);
}
