package com.fls.animecommunity.animesanctuary.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.dto.LoginRequest;
import com.fls.animecommunity.animesanctuary.dto.LoginResponse;
import com.fls.animecommunity.animesanctuary.service.impl.MemberService;

@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MemberService memberService;

    @PostMapping("/api/members/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 요청 로그
        log.info("로그인 요청 도착: {}", loginRequest.getUsernameOrEmail());

        // MemberService를 통해 로그인 검증
        Long userId = memberService.validateLogin(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
        
        if (userId != null) {
            // 로그인 성공 시
            log.info("로그인 성공: userId={}", userId);
            return ResponseEntity.ok(new LoginResponse("로그인 성공", userId));
        } else {
            // 로그인 실패 시
            log.warn("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다."));
        }
    }
}
