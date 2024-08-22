package com.example.fastbuymicroservices.domain.user.dto;

import com.example.fastbuymicroservices.domain.user.entity.User;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignupRequestDto {

    private String username;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;

    public User toEntity(PasswordEncoder encoder) {
        return User.builder()
                .username(encoder.encode(username))
                .address(encoder.encode(address))
                .phoneNumber(encoder.encode(phoneNumber))
                .email(email)
                .password(encoder.encode(password))
                .build();
    }
}