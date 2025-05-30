package com.example.ShoppApp.controller;

import com.example.ShoppApp.common.Gender;
import com.example.ShoppApp.dto.request.UserPasswordRequestDTO;
import com.example.ShoppApp.dto.request.UserRequestDTO;
import com.example.ShoppApp.dto.request.UserUpdateRequestDTO;
import com.example.ShoppApp.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mockup/user")
@Tag(name = "Mockup User Controller")
public class MockupUserController {

    @Operation(summary = "Test API" , description = "Mo ta chi tiet")
    @GetMapping("/list")
    // sample => để mockup dữ liệu cho bên fe làm
    public Map<String , Object> getList(@RequestParam(required = false) String keyword,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size){

        //c1:
       UserResponse userResponse1 = new UserResponse();
       userResponse1.setId(1L);
       userResponse1.setFistName("son");
       userResponse1.setLastName("Java");
       userResponse1.setGender(Gender.FEMALE);
       userResponse1.setBirthday(new Date());
       userResponse1.setUserName("admin");
       userResponse1.setEmail("admin@gmail.com");
       userResponse1.setPhone("0978693175");

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2L);
        userResponse2.setFistName("sonLai");
        userResponse2.setLastName("pyton");
        userResponse2.setGender(Gender.FEMALE);
        userResponse2.setBirthday(new Date());
        userResponse2.setUserName("product");
        userResponse2.setEmail("product@gmail.com");
        userResponse2.setPhone("0367853250");

        List<UserResponse> userList = List.of(userResponse1, userResponse2);

        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status" , HttpStatus.OK.value());
        result.put("message" , "user list");
        result.put("data" , userList);
        return result;
    }

    @Operation(summary = "Get user detail" , description = "API retrieve user detail by ID from database")
    @GetMapping("/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable Long userId){

        UserResponse userNameDetail = new UserResponse();
        userNameDetail.setId(1L);
        userNameDetail.setFistName("son");
        userNameDetail.setLastName("Java");
        userNameDetail.setGender(Gender.FEMALE);
        userNameDetail.setBirthday(new Date());
        userNameDetail.setUserName("admin");
        userNameDetail.setEmail("admin@gmail.com");
        userNameDetail.setPhone("0978693175");

        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message" , "user");
        result.put("data" , userNameDetail);

        return result;

    }

    @Operation(summary = "Create User" , description = "API add new user to database")
    @PostMapping("/add")
    public Map<String, Object> createUser(UserRequestDTO request) {

        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status" , HttpStatus.CREATED.value());
        result.put("message", " User created successfully");
        result.put("data", 3);
        return  result;
    }

    @Operation(summary = "Update User" , description = "API update user to database")
    @PutMapping("/und")
    public Map<String, Object> updateUser(UserUpdateRequestDTO request) {

        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status" , HttpStatus.ACCEPTED.value());
        result.put("message", " User update successfully");
        result.put("data", 3);
        return  result;
    }

    @Operation(summary = "Change Password" , description = "API change password user to database")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(UserPasswordRequestDTO request) {

        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status" , HttpStatus.NO_CONTENT.value());
        result.put("message", "password updated successfully");
        result.put("data", 3);
        return  result;
    }

    @Operation(summary = "Inactivate  User" , description = "API active user from database")
    @DeleteMapping("/{userId}/del")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {

        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status" , HttpStatus.RESET_CONTENT.value());
        result.put("message", "User deleted successfully");
        result.put("data", "");
        return  result;
    }
}
