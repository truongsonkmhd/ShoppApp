package com.example.ShoppApp.sevice.impl;

import com.example.ShoppApp.controller.request.UserCreationRequest;
import com.example.ShoppApp.controller.request.UserPasswordRequest;
import com.example.ShoppApp.controller.request.UserUpdateRequest;
import com.example.ShoppApp.model.UserResponse;
import com.example.ShoppApp.sevice.UserService;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Server
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public List<UserResponse> findAll() {
        return null;
    }

    @Override
    public UserResponse findById(Long id) {
        return null;
    }

    @Override
    public UserResponse findByUsername(String username) {
        return null;
    }

    @Override
    public UserResponse findByEmail(String email) {
        return null;
    }

    @Override
    public int save(UserUpdateRequest req) {
        return 0;
    }

    @Override
    public void update(UserCreationRequest req) {

    }

    @Override
    public void changePassword(UserPasswordRequest oldPassword) {

    }

    @Override
    public void delete(Long id) {

    }
}
