package com.example.ShoppApp.dto.request;

import com.example.ShoppApp.common.Gender;
import com.example.ShoppApp.common.UserType;
import com.example.ShoppApp.validator.EnumValue;
import com.example.ShoppApp.validator.GenderSubset;
import com.example.ShoppApp.validator.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.example.ShoppApp.common.Gender.*;

@Getter
public class UserRequestDTO implements Serializable {
    @NotBlank(message = "firstName must be not blank") // Khong cho phep gia tri blank
    private String firstName;

    @NotNull(message = "lastName must be not null") // Khong cho phep gia tri null
    private String lastName;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})// cách 2 validation
    private Gender gender;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthday;

    @NotNull(message = "username must be not null")
    private String userName;

    @Email(message = "email invalid format") // Chi chap nhan nhung gia tri dung dinh dang email
    //@Pattern(regexp = "^\\d{10}$", message = "phone invalid format")
    private String email;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)// cách 3 validation
    private UserType type;

    @NotEmpty(message = "addresses can not empty")
    private Set<AddressRequestDTO> addresses;// home company
}
