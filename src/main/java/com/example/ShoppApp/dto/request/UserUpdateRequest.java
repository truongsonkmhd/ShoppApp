package com.example.ShoppApp.dto.request;

import com.example.ShoppApp.common.Gender;
import com.example.ShoppApp.model.AddressEntity;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
public class UserUpdateRequest implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String userName;
    private String email;
    private String phone;
    private List<AddressEntity> addresses;
}
