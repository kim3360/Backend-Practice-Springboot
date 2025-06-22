// src/main/java/com/dongyang/yongmi01/controller/AuthController.java
package com.dongyang.Taewoo.controller;

import com.dongyang.Taewoo.dto.AuthResponseDTO;
import com.dongyang.Taewoo.dto.LoginRequestDTO;
import com.dongyang.Taewoo.entity.User;
import com.dongyang.Taewoo.repository.UserRepository;
import com.dongyang.Taewoo.config.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");
        }
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(201).body("회원가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        log.info("로그인 시도: username={}", dto.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.createToken(authentication);
            AuthResponseDTO response = new AuthResponseDTO("Bearer", token);
            log.info("로그인 성공: username={}", dto.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("로그인 실패: username={}, error={}", dto.getUsername(), e.getMessage());
            return ResponseEntity.status(403).body("로그인 실패: " + e.getMessage());
        }
    }
}