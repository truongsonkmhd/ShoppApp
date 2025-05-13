package com.example.ShoppApp.controller;

import com.example.ShoppApp.dto.request.SignInRequest;
import com.example.ShoppApp.dto.response.TokenResponse;
import com.example.ShoppApp.sevice.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j(topic = "AUTHENTICATION-CONTROLLER")
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Access token" , description = "Get access token and refresh token by username and password")
    @PostMapping("/access-token")
    public TokenResponse getAccessToken(@RequestBody SignInRequest accessToken){
        log.info("Access token request");
        return authenticationService.getAccessToken(accessToken);
    }

    @Operation(summary = "Refresh token" , description = "Get access token token by refresh")
    @PostMapping("/refresh-token")
    public TokenResponse getRefreshToken(@RequestBody SignInRequest refreshToken){
        log.info("Refresh token request");
        return TokenResponse.builder().accessToken("DUMMY-ACCESS-TOKEN").refreshToken("DUMMY-REFRESH-TOKEN").build();
    }
}
