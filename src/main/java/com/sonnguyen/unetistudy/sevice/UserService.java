package com.example.ShoppApp.sevice;

import com.example.ShoppApp.dto.request.UserRequestDTO;
import com.example.ShoppApp.dto.request.UserPasswordRequestDTO;
import com.example.ShoppApp.dto.request.UserUpdateRequestDTO;
import com.example.ShoppApp.dto.response.UserPageResponse;
import com.example.ShoppApp.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserPageResponse getAllUsersWithSortBy(String sort, int page, int size);

    UserPageResponse getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, List<String> sorts);

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    long saveUser(UserRequestDTO user);

    void update( long userId , UserUpdateRequestDTO user);

    void changePassword(UserPasswordRequestDTO oldPassword);

    void delete(Long id);
}
