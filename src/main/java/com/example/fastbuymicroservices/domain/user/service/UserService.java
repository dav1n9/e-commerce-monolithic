package com.example.fastbuymicroservices.domain.user.service;

import com.example.fastbuymicroservices.domain.user.dto.SignupRequestDto;
import com.example.fastbuymicroservices.domain.user.entity.User;
import com.example.fastbuymicroservices.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto request) {

        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(request.getEmail());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        User user = request.toEntity(passwordEncoder);
        userRepository.save(user);
    }
}