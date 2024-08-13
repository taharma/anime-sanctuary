package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fls.animecommunity.animesanctuary.dto.LoginRequestDTO;
import com.fls.animecommunity.animesanctuary.dto.MemberRegistrationDTO;
import com.fls.animecommunity.animesanctuary.dto.MemberResponseDTO;
import com.fls.animecommunity.animesanctuary.dto.LoginResponseDTO;
import com.fls.animecommunity.animesanctuary.model.Member;
import com.fls.animecommunity.animesanctuary.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<MemberResponseDTO> register(@RequestBody MemberRegistrationDTO registrationDTO) {
        // DTO를 도메인 모델로 변환
        Member member = new Member();
        member.setUsername(registrationDTO.getUsername());
        member.setPassword(registrationDTO.getPassword());
        member.setEmail(registrationDTO.getEmail());
        
        // 서비스 메소드 호출
        Member registeredMember = memberService.register(member);
        MemberResponseDTO responseDTO = new MemberResponseDTO(registeredMember);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        Member member = memberService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        if (member != null) {
            request.getSession().setAttribute("user", member);
            MemberResponseDTO memberDTO = new MemberResponseDTO(member);
            LoginResponseDTO responseDTO = new LoginResponseDTO("Login successful", memberDTO);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(401).body(new LoginResponseDTO("Invalid username or password", null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id, @RequestParam("password") String password, HttpServletRequest request) {
        Member loggedInMember = (Member) request.getSession().getAttribute("user");
        if (loggedInMember == null) {
            return ResponseEntity.status(403).build(); // 로그인하지 않은 경우, Forbidden
        }
        
        boolean isDeleted = memberService.deleteMember(id, password);
        if (isDeleted) {
            return ResponseEntity.ok().build(); // 회원 삭제 성공
        } else {
            return ResponseEntity.status(401).build(); // 비밀번호가 일치하지 않음, Unauthorized
        }
    }
    
    @GetMapping("/findByEmail")
    public ResponseEntity<MemberResponseDTO> findByEmail(@RequestParam String email) {
        Member member = memberService.findByEmail(email);
        if (member != null) {
            MemberResponseDTO responseDTO = new MemberResponseDTO(member);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(404).build(); // Not Found
        }
    }
}
