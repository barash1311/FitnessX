package com.fitness.activityservice.dto.Activity;

import com.fitness.activityservice.entity.enums.ActivityType;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
public class ActivityRequest {
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurnt;
    private LocalDateTime startTime;
    private Map<String, Object> additionalInfo;
}
