package com.example.ShoppApp.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
public class UserCreationRequest implements Serializable {
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthday;
    private String userName;
    private String email;
    private String phone;
    private List<AddressRequest> addresses;// home company
}
