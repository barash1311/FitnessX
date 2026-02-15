package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.Activity.ActivityRequest;
import com.fitness.activityservice.dto.Activity.ActivityResponse;
import com.fitness.activityservice.entity.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class ActivityServiceImplementation implements ActivityService{
    private final ActivityRepository activityRepository;
    @Override
    public ActivityResponse track(ActivityRequest request) {
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
