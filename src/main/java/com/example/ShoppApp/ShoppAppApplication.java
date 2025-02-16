package com.example.ShoppApp;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppAppApplication {

	@Value("${jwt.secretKey}")
	private String jwtKey;

	public static void main(String[] args) {
		SpringApplication.run(ShoppAppApplication.class, args);
	}

	@PostConstruct
	public void Test(){
		System.out.println("JwtKey: " + jwtKey);
	}

}
