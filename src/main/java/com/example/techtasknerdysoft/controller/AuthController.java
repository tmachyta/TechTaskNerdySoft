package com.example.techtasknerdysoft.controller;

import com.example.techtasknerdysoft.dto.user.UserLoginRequestDto;
import com.example.techtasknerdysoft.dto.user.UserLoginResponseDto;
import com.example.techtasknerdysoft.dto.user.UserRegistrationRequest;
import com.example.techtasknerdysoft.dto.user.UserResponseDto;
import com.example.techtasknerdysoft.exception.RegistrationException;
import com.example.techtasknerdysoft.security.AuthenticationService;
import com.example.techtasknerdysoft.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management", description = "Endpoints for managing authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @PermitAll
    @Operation(summary = "Login", description = "Login method to authenticate users")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/register")
    @PermitAll
    @Operation(summary = "Register", description = "Register method to register users")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequest request)
            throws RegistrationException {
        return userService.register(request);
    }
}
