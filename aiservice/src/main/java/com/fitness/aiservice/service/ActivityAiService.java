package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.dto.RecommendationResponse;
import com.fitness.aiservice.models.Activity;
import com.fitness.aiservice.models.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {
    private final GeminiService geminiService;

    public RecommendationResponse generateRecommendations(Activity activity){
        String prompt=createPromptForActivity(activity);
        String aiResponse=geminiService.getRecommendations(prompt);
        log.info("RESPONSE FROM AI {} ", aiResponse);
        return processAIResponse(activity,aiResponse);
    }

    private RecommendationResponse processAIResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);

            JsonNode textNode = rootNode
                    .path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text");

            if (textNode.isMissingNode() || textNode.isNull()) {
                log.error("Text node not found in AI response");
                return null;
            }

            
            String rawText = textNode.asText().replaceFirst("(?i)^json\\s*", "").trim();


            String sanitized = rawText
                    .replaceAll("\\r", "")
                    .replaceAll("\\n", "")
                    .trim();


            JsonNode cleanedNode = mapper.readTree(sanitized);
            log.info("Response from Cleaned AI: {}", cleanedNode.toPrettyString());


            return mapper.treeToValue(cleanedNode.path("analysis"), RecommendationResponse.class);

        } catch (Exception e) {
            log.error("Error cleaning AI response", e);
            return null;
        }
    }

    private String createPromptForActivity(Activity activity){
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurnt(),
                activity.getAdditionalInfo()
        );
    }
}
