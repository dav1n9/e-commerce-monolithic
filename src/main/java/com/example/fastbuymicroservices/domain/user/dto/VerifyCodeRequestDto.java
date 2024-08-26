package com.example.fastbuymicroservices.domain.user.dto;

import lombok.Getter;

@Getter
public class VerifyCodeRequestDto {
    private String email;
    private String code;
}
