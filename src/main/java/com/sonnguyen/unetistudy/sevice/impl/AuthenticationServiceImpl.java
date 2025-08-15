package com.example.ShoppApp.sevice.impl;

import com.example.ShoppApp.dto.request.SignInRequest;
import com.example.ShoppApp.dto.response.TokenResponse;
import com.example.ShoppApp.repository.UserRepository;
import com.example.ShoppApp.sevice.AuthenticationService;
import com.example.ShoppApp.sevice.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse getAccessToken(SignInRequest request) {
        log.info("Get access token");
        try {
            // Thực hiện xác thực với username và password
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            log.info("isAuthenticated = {}", authenticate.isAuthenticated());
            log.info("Authorities: {}", authenticate.getAuthorities().toString());

            // Nếu xác thực thành công, lưu thông tin vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (BadCredentialsException | DisabledException e) {
            log.error("errorMessage: {}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }

        // Get user
        var user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            log.info("LỖI LÒI");
            throw new UsernameNotFoundException(request.getUsername());
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), user.getAuthorities());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername(), user.getAuthorities());

        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

}

    @Override
    public TokenResponse getRefreshToken(String request) {
        return null;
    }
}
