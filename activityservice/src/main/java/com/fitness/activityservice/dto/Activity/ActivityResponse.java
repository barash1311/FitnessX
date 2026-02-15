package com.fitness.activityservice.dto.Activity;

import com.fitness.activityservice.entity.enums.ActivityType;
import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class ActivityResponse {
    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurnt;
    private LocalDateTime startTime;
    private Map<String, Object> additionalInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
