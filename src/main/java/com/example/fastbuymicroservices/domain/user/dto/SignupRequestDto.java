package com.example.fastbuymicroservices.domain.user.dto;

import com.example.fastbuymicroservices.domain.user.entity.User;
import com.example.fastbuymicroservices.global.common.EncryptionUtil;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String username;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;

    public User toEntity(EncryptionUtil encryptionUtil, String password) throws Exception {
        return User.builder()
                .username(encryptionUtil.encrypt(username))
                .address(encryptionUtil.encrypt(address))
                .phoneNumber(encryptionUtil.encrypt(phoneNumber))
                .email(email)
                .password(password)
                .build();
    }
}