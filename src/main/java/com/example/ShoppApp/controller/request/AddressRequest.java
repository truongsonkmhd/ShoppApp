package com.example.ShoppApp.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter // đây là javaBean
public class AddressRequest implements Serializable {
    private String apartmentNumber;
    private String floor;
    private String building;
    private String streetNumber;
    private String street;
    private String city;
    private String country;
    private Integer addressType;
}
