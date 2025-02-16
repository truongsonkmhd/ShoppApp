package com.example.ShoppApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tbl_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // phù hợp với PostgreSQL
    private Long id;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "gender", length = 255)
    private String gender;

    @Column(name = "birthday", length = 255)
    private Date birthday;

    @Column(name = "user_name", length = 255)
    private String userName;

    @Column(name = "first_name", length = 255)
    private String email;

    @Column(name = "first_name", length = 255)
    private String phone;

    @Column(name = "first_name", length = 255)
    private String username;

    @Column(name = "first_name", length = 255)
    private String password;

/*
    @Column(name = "first_name", length = 255)
    //private UserType type;

    @Column(name = "first_name", length = 255)
    private  UserStatus status;
*/

    @Column(name = "first_name", length = 255)
    private Date createdAt;

    @Column(name = "first_name", length = 255)
    private Date updatedAt;

}
