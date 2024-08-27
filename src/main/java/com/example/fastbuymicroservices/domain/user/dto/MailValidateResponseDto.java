package com.example.fastbuymicroservices.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailValidateResponseDto {
    private String code;
    private String message;

    public MailValidateResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
