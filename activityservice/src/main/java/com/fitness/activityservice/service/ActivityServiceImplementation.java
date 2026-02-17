package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.Activity.ActivityRequest;
import com.fitness.activityservice.dto.Activity.ActivityResponse;
import com.fitness.activityservice.entity.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ActivityServiceImplementation implements ActivityService{
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String,Activity> kafkaTemplate;
    @Value("${kafka.topic.name}")
    private String topicName;
    @Override
    public ActivityResponse track(ActivityRequest request) {
        // Ensure userId is a valid UUID string to keep cross-service contract consistent
        try {
            UUID.fromString(request.getUserId());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid userId format. Expected UUID: " + request.getUserId());
        }
        boolean isValid = userValidationService.validateUser(request.getUserId());
        if (!isValid) {
            throw new RuntimeException("User not valid: " + request.getUserId());
        }
        LocalDateTime startTime = (request.getStartTime() != null)
                ? request.getStartTime()
                : LocalDateTime.now();

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurnt(request.getCaloriesBurnt())
                .startTime(startTime)
                .additionalInfo(request.getAdditionalInfo())
                .build();
        activityRepository.save(activity);
        try{
            kafkaTemplate.send(topicName,activity.getUserId(),activity);
        } catch (Exception e){
            e.printStackTrace();
        }
        return mapToResponse(activity);
    }
    public ActivityResponse mapToResponse(Activity activity){
        return ActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .duration(activity.getDuration())
                .caloriesBurnt(activity.getCaloriesBurnt())
                .startTime(activity.getStartTime())
                .additionalInfo(activity.getAdditionalInfo())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }
}
