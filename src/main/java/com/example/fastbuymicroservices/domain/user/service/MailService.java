package com.example.fastbuymicroservices.domain.user.service;

import com.example.fastbuymicroservices.domain.user.dto.VerifyCodeRequestDto;
import com.example.fastbuymicroservices.domain.user.repository.UserRepository;
import com.example.fastbuymicroservices.global.common.RedisUtil;
import com.example.fastbuymicroservices.global.exception.BusinessException;
import com.example.fastbuymicroservices.global.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void verifyCode(VerifyCodeRequestDto request) {
        if (redisUtil.getData(request.getCode()) == null ||
                !redisUtil.getData(request.getCode()).equals(request.getEmail())) {
            throw new BusinessException(ErrorCode.INVALID_VERIFICATION_CODE);
        }
    }

    public String sendSimpleMessage(String sendEmail) throws MessagingException {
        String number = createNumber(); // 랜덤 인증번호 생성

        checkDuplicatedEmail(sendEmail);

        MimeMessage message = createMail(sendEmail, number);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.MAIL_SEND_FAILURE);
        }

        redisUtil.setDataExpire(number, sendEmail, 60 * 5L); // 5분
        return number;
    }

    public MimeMessage createMail(String mail, String number) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setSubject("회원가입 이메일 인증");
        String body = "";
        body += "<h3>요청하신 인증 번호입니다.</h3>";
        body += "<br><p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p><br>";
        body += "<h1>" + number + "</h1>";
        body += "<h3>감사합니다.</h3>";
        message.setText(body, "UTF-8", "html");

        return message;
    }

    public String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) { // 인증 코드 8자리
            int index = random.nextInt(3); // 0~2까지 랜덤, 랜덤값으로 switch 문 실행

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97)); // 소문자
                case 1 -> key.append((char) (random.nextInt(26) + 65)); // 대문자
                case 2 -> key.append(random.nextInt(10)); // 숫자
            }
        }
        return key.toString();
    }

    private void checkDuplicatedEmail(String email) {
        userRepository.findByEmail(email).ifPresent(m -> {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        });
    }
}
