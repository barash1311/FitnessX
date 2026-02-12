package com.fitness.userservice.service;

import com.fitness.userservice.dtos.RegisterRequest;
import com.fitness.userservice.dtos.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface userService {
    UserResponse register(RegisterRequest registerRequest);
}
