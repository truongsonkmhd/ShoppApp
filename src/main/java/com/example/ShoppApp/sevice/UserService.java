package com.example.ShoppApp.sevice;

import com.example.ShoppApp.controller.request.UserCreationRequest;
import com.example.ShoppApp.controller.request.UserPasswordRequest;
import com.example.ShoppApp.controller.request.UserUpdateRequest;
import com.example.ShoppApp.model.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    int save(UserUpdateRequest req);

    void update(UserCreationRequest req);

    void changePassword(UserPasswordRequest oldPassword);

    void delete(Long id);
}
