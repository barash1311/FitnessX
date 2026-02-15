package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.Activity.ActivityRequest;
import com.fitness.activityservice.dto.Activity.ActivityResponse;
import org.springframework.stereotype.Service;

public interface ActivityService {
    ActivityResponse track(ActivityRequest request);
}
