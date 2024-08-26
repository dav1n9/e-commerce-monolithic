package com.example.fastbuymicroservices.domain.user.controller;

import com.example.fastbuymicroservices.domain.user.dto.MailValidateRequestDto;
import com.example.fastbuymicroservices.domain.user.dto.MailValidateResponseDto;
import com.example.fastbuymicroservices.domain.user.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MailController {

    private final MailService mailService;

    @PostMapping("/email-validate")
    public ResponseEntity<MailValidateResponseDto> mailConfirm(@RequestBody MailValidateRequestDto request) throws Exception {
        String code = mailService.sendSimpleMessage(request.getEmail());
        log.info("인증코드 : " + code);
        return ResponseEntity.ok().body(new MailValidateResponseDto(code, "이메일 인증 메일이 전송되었습니다."));
    }


}
