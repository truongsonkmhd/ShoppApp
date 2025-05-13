package com.example.ShoppApp.sevice;

import com.example.ShoppApp.dto.request.SignInRequest;
import com.example.ShoppApp.dto.response.TokenResponse;
import org.antlr.v4.runtime.Token;

public interface AuthenticationService {
    TokenResponse getAccessToken(SignInRequest request);

    TokenResponse getRefreshToken(String request);
}
