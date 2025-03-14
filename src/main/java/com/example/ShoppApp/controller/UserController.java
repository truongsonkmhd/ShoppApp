package com.example.ShoppApp.controller;

import com.example.ShoppApp.controller.request.UserCreationRequest;
import com.example.ShoppApp.controller.request.UserPasswordRequest;
import com.example.ShoppApp.controller.request.UserUpdateRequest;
import com.example.ShoppApp.model.UserResponse;
import com.example.ShoppApp.sevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
@Slf4j(topic = "USER_CONTROLLER")
@RequiredArgsConstructor
public class UserController {
// có 3 cách khởi tạo bind
    // c1: sử dụng toán tử new
    // c2 RequiredArgsConstructor
    // như ví dụ dưới (đỡ p viết dòng này:)
    /*public UserController(UserService userService){
        this.userService = userService;
    }
    */
    private final UserService userService;

    @PostConstruct
    public void init() {
        System.out.println("UserService đã được inject: " + userService);
    }

    // c3: sử dụng @Autowired
    /*
        @Autowired
        private final UserService userService;
    */

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
       userResponse1.setGender("nam");
       userResponse1.setBirthday(new Date());
       userResponse1.setUserName("admin");
       userResponse1.setEmail("admin@gmail.com");
       userResponse1.setPhone("0978693175");

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2L);
        userResponse2.setFistName("sonLai");
        userResponse2.setLastName("pyton");
        userResponse2.setGender("nam");
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
        userNameDetail.setGender("nam");
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
    public ResponseEntity<Object> createUser(@RequestBody  UserCreationRequest request) {

        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status" , HttpStatus.CREATED.value());
        result.put("message", " User created successfully");
        result.put("data", userService.save(request));

        return  new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Update User" , description = "API update user to database")
    @PutMapping("/upd")
    public Map<String, Object> updateUser(@RequestBody UserUpdateRequest request) {
        log.info("Updating user: {}" , request);

        userService.update(request);
        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status" , HttpStatus.ACCEPTED.value());
        result.put("message", " User update successfully");
        result.put("data", 3);
        return  result;
    }

    @Operation(summary = "Change Password" , description = "API change password user to database")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(@RequestBody UserPasswordRequest request) {
        log.info("Changing password for user: {}" , request);

        userService.changePassword(request);

        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status" , HttpStatus.NO_CONTENT.value());
        result.put("message", "password updated successfully");
        result.put("data", 3);
        return  result;
    }

    @Operation(summary = "Inactivate  User" , description = "API active user from database")
    @DeleteMapping("/{userId}/del")
    public Map<String, Object> deleteUser(@RequestBody Long userId) {

        Map<String , Object> result = new LinkedHashMap<>();
        result.put("status" , HttpStatus.RESET_CONTENT.value());
        result.put("message", "User deleted successfully");
        result.put("data", "");
        return  result;
    }
}
