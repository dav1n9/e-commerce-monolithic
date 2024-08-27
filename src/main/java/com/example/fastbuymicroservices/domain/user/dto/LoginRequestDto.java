package com.example.fastbuymicroservices.domain.user.dto;

import com.example.fastbuymicroservices.global.common.EncryptDecryptConverter;
import jakarta.persistence.Convert;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @Convert(converter = EncryptDecryptConverter.class)
    private String email;

    private String password;
}