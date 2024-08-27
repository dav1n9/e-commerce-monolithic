package com.example.fastbuymicroservices.domain.user.service;

import com.example.fastbuymicroservices.domain.user.dto.SignupRequestDto;
import com.example.fastbuymicroservices.domain.user.dto.UpdatePasswordRequestDto;
import com.example.fastbuymicroservices.domain.user.entity.User;
import com.example.fastbuymicroservices.domain.user.repository.UserRepository;
import com.example.fastbuymicroservices.domain.wishlist.entity.Wishlist;
import com.example.fastbuymicroservices.domain.wishlist.repository.WishlistRepository;
import com.example.fastbuymicroservices.global.exception.BusinessException;
import com.example.fastbuymicroservices.global.exception.ErrorCode;
import com.example.fastbuymicroservices.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto request) throws Exception {

        Optional<User> checkEmail = userRepository.findByEmail(request.getEmail());
        if (checkEmail.isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = request.toEntity(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Wishlist wishlist = Wishlist.builder()
                .name("기본 위시리스트")
                .user(user)
                .build();
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void updatePassword(HttpServletRequest req, HttpServletResponse res,
                               Long userId, UpdatePasswordRequestDto request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        jwtUtil.deleteToken(req, res);
    }
}