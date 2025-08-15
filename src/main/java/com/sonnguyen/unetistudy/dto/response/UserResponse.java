package com.example.ShoppApp.dto.response;

import com.example.ShoppApp.common.Gender;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private Long id;
    private String fistName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String userName;
    private String email;
    private String phone;
    //more
}
