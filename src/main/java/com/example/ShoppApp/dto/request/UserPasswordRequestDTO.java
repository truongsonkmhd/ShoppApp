package com.example.ShoppApp.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserPasswordRequestDTO implements Serializable {
    private Long id;
    private String password;
    private String confirmPassword;
}
