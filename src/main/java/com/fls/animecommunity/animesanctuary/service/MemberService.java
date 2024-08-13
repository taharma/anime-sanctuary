package com.fls.animecommunity.animesanctuary.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fls.animecommunity.animesanctuary.exception.InvalidCredentialsException;
import com.fls.animecommunity.animesanctuary.exception.InvalidPasswordException;
import com.fls.animecommunity.animesanctuary.exception.UserNotFoundException;
import com.fls.animecommunity.animesanctuary.model.Member;
import com.fls.animecommunity.animesanctuary.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 등록 (트랜잭션 적용)
    @Transactional
    public Member register(Member member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // 비밀번호 암호화 후 저장
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    // 로그인
    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username);
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
        return member;
    }

    public boolean deleteMember(Long id, String password) {
    	Member member = memberRepository.findById(id).orElse(null);
    	if (member == null) {
    		throw new UserNotFoundException("User not found with ID: " + id);
    	}
    	if (!passwordEncoder.matches(password, member.getPassword())) {
    		throw new InvalidPasswordException("Password is incorrect");
    	}
    	memberRepository.deleteById(id);
    	return true;
    }
    
    public Member findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return member;
    }
    
}
