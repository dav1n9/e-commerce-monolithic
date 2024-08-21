package com.example.fastbuymicroservices.domain.user.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String username;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;

}