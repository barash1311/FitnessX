package com.fitness.userservice.controller;

import com.fitness.userservice.dtos.RegisterRequest;
import com.fitness.userservice.dtos.UserResponse;
import com.fitness.userservice.service.userService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final userService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid  @RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> gerUserProfile(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

}
