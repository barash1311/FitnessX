package com.fitness.aiservice.service;

import com.fitness.aiservice.models.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {
    private final GeminiService geminiService;

    public void generateRecommendations(Activity activity){
        String prompt=createPromoptForActivity(activity);
        log.info()
    }
}
