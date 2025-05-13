package com.example.ShoppApp.controller;

import com.example.ShoppApp.configuration.Translator;
import com.example.ShoppApp.dto.request.UserRequestDTO;
import com.example.ShoppApp.dto.request.UserPasswordRequestDTO;
import com.example.ShoppApp.dto.request.UserUpdateRequestDTO;
import com.example.ShoppApp.dto.response.ResponseData;
import com.example.ShoppApp.dto.response.ResponseError;
import com.example.ShoppApp.dto.response.UserPageResponse;
import com.example.ShoppApp.dto.response.UserResponse;
import com.example.ShoppApp.exception.ResourceNotFoundException;
import com.example.ShoppApp.sevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
@Slf4j(topic = "USER_CONTROLLER")
@RequiredArgsConstructor
public class UserController {
    // có 3 cách khởi tạo bind
    // c1: sử dụng toán tử new
    // c2 RequiredArgsConstructor
    private final UserService userService;
    // như ví dụ dưới (đỡ p viết dòng này:)
    /*public UserController(UserService userService){
        this.userService = userService;
    }
    */

    private static final String ERROR_MESSAGE = "errorMessage={}";

    // c3: sử dụng @Autowired
    /*
        @Autowired
        private final UserService userService;
    */

    @Operation(summary = "Get User Sorted", description = "API retrieve user sorted ")
    @GetMapping("/list")
    public ResponseData<UserPageResponse> getAllUsersWithSortBy(@RequestParam(required = false) String sortBy,
                                                  @RequestParam(defaultValue = "0", required = false) int pageNo,
                                                  @RequestParam(defaultValue = "20") int pageSize) {
        log.info("Request get all users with sort by");

        try {
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Get All Users With Sort by",
                    userService.getAllUsersWithSortBy( sortBy, pageNo, pageSize));
        } catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(summary = "Get User Sorted multiple column", description = "API retrieve user sorted multiple column")
    @GetMapping("/list_sort_multiple")
    public ResponseData<UserPageResponse> getAllUsersWithSortByMultipleColumns(@RequestParam(required = false) List<String> sort,
                                                                @RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                @RequestParam(defaultValue = "20") int pageSize) {
        log.info("Request get all of users with sort by multiple column");

        try {
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Get All Users With Sort by",
                    userService.getAllUsersWithSortByMultipleColumns( pageNo, pageSize, sort));
        } catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID from database")
    @GetMapping("/{userId}")
    public ResponseData<UserResponse> getUserDetail(@PathVariable @Min(1) Long userId) {
        log.info("Get user detail by ID: {}", userId);

        try{
            return new ResponseData<>(HttpStatus.OK.value(), "get User Detail", userService.findById(userId));
        }catch (ResourceNotFoundException e){
            log.error("errorMessager = {}" , e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }

    @Operation(summary = "Create User", description = "API add new user to database")
    @PostMapping("/add")
    public ResponseData<Long> createUser(@RequestBody UserRequestDTO request) {

        log.info("Request add user,{} {}", request.getFirstName(), request.getLastName());
        try{
            long userId = userService. saveUser(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), Translator.toLocale("user.add.success"),userId);
        }catch (Exception e){
            log.info("errorMessage = {}", e.getMessage() , e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add user fail");
        }

//        Map<String, Object> result = new LinkedHashMap<>();
//        result.put("status", HttpStatus.CREATED.value());
//        result.put("message", " User created successfully");
//        result.put("data", userService.saveUser(request));
//
//        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Update User", description = "API update user to database")
    @PutMapping("/upd/{userId}")
    public ResponseData<Void> updateUser(@PathVariable @Min(1) long userId, @RequestBody UserUpdateRequestDTO request) {
        log.info("Updating user: {}", request);
        try{
            userService.update(userId,request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), Translator.toLocale("user.upd.success"));
        } catch (Exception e){
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update user fail");
        }

    }

    @Operation(summary = "Change Password", description = "API change password user to database")
    @PatchMapping("/change-pwd")
    public ResponseData<Void> changePassword(@RequestBody UserPasswordRequestDTO request) {
        log.info("Changing password for user: {}", request);

        try{
            userService.changePassword(request);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("password.change.success"));
        } catch (Exception e){
            log.error(ERROR_MESSAGE,e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "change-pwd fail");
        }

    }

    @Operation(summary = "Inactivate  User", description = "API active user from database")
    @DeleteMapping("/del/{userId}")
    public ResponseData<Void> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user: {}", userId);
        try{
            userService.delete(userId);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("user.del.success"));
        }catch (Exception e){
            log.error(ERROR_MESSAGE, e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "delete user fail");
        }
    }
}
