package com.example.ShoppApp.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SignInRequest implements Serializable {
    private String username;
    private String password;
    private String platform; // web , mobile , miniApp
    private String deviceToken; // thieets bij mobile cần cái này để insert vào database de có action push notifi nào sẽ push thneo deviceToken đến đến app đấy
    private String versionApp;
}
