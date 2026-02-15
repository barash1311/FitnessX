package com.fitness.userservice.service;

import com.fitness.userservice.dtos.RegisterRequest;
import com.fitness.userservice.dtos.UserResponse;
import com.fitness.userservice.models.User;
import com.fitness.userservice.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class userServiceImplementation implements userService {

    private final userRepository userRepository;

    @Override
    public UserResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword()) // ideally encode before saving
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        userRepository.save(user);
        return mapToUserResponse(user);
    }

    @Override
    public UserResponse getUserProfile(UUID userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not found"));

        return mapToUserResponse(user);
    }

    @Override
    public Boolean existByUserId(UUID userId) {
        return  userRepository.existsById(userId);
    }


    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .build();
        }
    }

