package com.example.ShoppApp.controller.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;
@Setter
@Getter
public class UserPageResponse extends PageResponseAbstract implements Serializable {
    private List<UserResponse> users;
}
