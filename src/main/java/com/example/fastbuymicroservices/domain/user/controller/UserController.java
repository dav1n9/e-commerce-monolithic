package com.example.fastbuymicroservices.domain.user.controller;

import com.example.fastbuymicroservices.domain.user.dto.SignupRequestDto;
import com.example.fastbuymicroservices.domain.user.dto.UpdatePasswordRequestDto;
import com.example.fastbuymicroservices.domain.user.service.UserService;
import com.example.fastbuymicroservices.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDto request) {
        userService.signup(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(HttpServletRequest req, HttpServletResponse res,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestBody UpdatePasswordRequestDto request) {
        userService.updatePassword(req, res, userDetails.getUser().getId(), request);
        return ResponseEntity.ok().build();
    }
}
