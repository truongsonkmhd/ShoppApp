package com.example.ShoppApp.sevice;

import com.example.ShoppApp.dto.request.UserCreationRequest;
import com.example.ShoppApp.dto.request.UserPasswordRequest;
import com.example.ShoppApp.dto.request.UserUpdateRequest;
import com.example.ShoppApp.dto.response.UserPageResponse;
import com.example.ShoppApp.dto.response.UserResponse;

public interface UserService {

    UserPageResponse findAll(String keyword, String sort, int page, int size);

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    long save(UserCreationRequest req);

    void update(UserUpdateRequest req);

    void changePassword(UserPasswordRequest oldPassword);

    void delete(Long id);
}
