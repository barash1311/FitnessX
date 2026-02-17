package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationServiceImplementation implements UserValidationService {
    private final WebClient userServiceWebClient;
    @Override
    public boolean validateUser(String userId){
        try{
        return Boolean.TRUE.equals(userServiceWebClient.get()
                .uri("/api/users/{userId}/validate", userId)
                .retrieve().bodyToMono(Boolean.class).block());
    }catch (WebClientResponseException e){
            e.printStackTrace();
        }
        return false;
    }

}

