package com.fls.animecommunity.animesanctuary.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fls.animecommunity.animesanctuary.model.Member;
import com.fls.animecommunity.animesanctuary.model.Scrap;
import com.fls.animecommunity.animesanctuary.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 생성자 주입
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
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    // 로그인
    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username);
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            return member;
        }
        return null;
    }

    // 회원 삭제 메서드, 비밀번호 확인 포함
    @Transactional
    public boolean deleteMember(Long id, String password) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 이메일로 사용자 찾기
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 프로필 조회
    public Member getProfile(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    // 프로필 수정
    @Transactional
    public Member updateProfile(Long id, Member updatedMember, String currentPassword) {
        Member existingMember = memberRepository.findById(id).orElse(null);
        if (existingMember != null) {
            // 비밀번호 변경 시 확인 절차
            if (updatedMember.getPassword() != null && !updatedMember.getPassword().isEmpty()) {
                if (!passwordEncoder.matches(currentPassword, existingMember.getPassword())) {
                    throw new IllegalArgumentException("Current password is incorrect");
                }
                existingMember.setPassword(passwordEncoder.encode(updatedMember.getPassword()));
            }
            existingMember.setName(updatedMember.getName());
            existingMember.setBirthdate(updatedMember.getBirthdate());
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setGender(updatedMember.getGender());
            return memberRepository.save(existingMember);
        }
        return null;
    }

    // 스크랩 목록 조회 (가정: 스크랩 데이터 모델과 관련 서비스가 필요)
    public List<Scrap> getScraps(Long userId) {
        // 스크랩 관련 서비스 또는 레포지토리 호출
        return new ArrayList<>(); // 임시 반환, 실제 구현 필요
    }
}
