package com.fls.animecommunity.animesanctuary.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fls.animecommunity.animesanctuary.model.UpdateProfileRequest;
import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.model.member.Role;
import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.repository.MemberRepository;
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final NoteRepository noteRepository;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, NoteRepository noteRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.noteRepository = noteRepository;
    }

    // 사용자 회원가입
    @Transactional
    public Member register(Member member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (member.getPassword() == null || member.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        System.out.println("Password before encoding: " + member.getPassword());

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    // 사용자 로그인
    public Member login(String usernameOrEmail, String password) {
        Member member = memberRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            return member;
        }
        return null;
    }

    // 회원 삭제
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

    // 프로필 업데이트
    @Transactional
    public Member updateProfile(Long id, UpdateProfileRequest updateRequest) {
        Member existingMember = memberRepository.findById(id).orElse(null);
        if (existingMember == null) {
            return null;
        }

        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            if (updateRequest.getCurrentPassword() == null ||
                !passwordEncoder.matches(updateRequest.getCurrentPassword(), existingMember.getPassword())) {
                throw new IllegalArgumentException("Current password is incorrect");
            }
            existingMember.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        existingMember.setUsername(updateRequest.getUsername() != null ? updateRequest.getUsername() : existingMember.getUsername());
        existingMember.setName(updateRequest.getName() != null ? updateRequest.getName() : existingMember.getName());
        existingMember.setBirth(updateRequest.getBirth() != null ? updateRequest.getBirth() : existingMember.getBirth());
        existingMember.setEmail(updateRequest.getEmail() != null ? updateRequest.getEmail() : existingMember.getEmail());
        existingMember.setGender(updateRequest.getGender() != null ? updateRequest.getGender() : existingMember.getGender());

        return memberRepository.save(existingMember);
    }

    // 프로필 이미지 업로드
    public String uploadProfileImage(Long userId, MultipartFile image) throws IOException {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        String uploadDir = "uploads/profile-images/";
        Files.createDirectories(Paths.get(uploadDir));

        String originalFilename = image.getOriginalFilename();
        String newFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        String imagePath = uploadDir + newFilename;

        File imageFile = new File(imagePath);
        image.transferTo(imageFile);

        member.setProfileImage(imagePath);
        memberRepository.save(member);

        return imagePath;
    }

    // 프로필 이미지 업데이트
    public void updateProfileImage(Long userId, String filePath) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        member.setProfileImage(filePath);
        memberRepository.save(member);
    }

    // 프로필 이미지 삭제
    public void deleteProfileImage(Long userId) throws IOException {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        String filePath = member.getProfileImage();
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    throw new IOException("Failed to delete file: " + filePath);
                }
            }

            member.setProfileImage(null);
            memberRepository.save(member);
        }
    }

    // 소셜 회원가입/로그인
    @Transactional
    public Member registerOrLoginWithSocial(String name, String email, String provider, String providerId) {
        Member existingMember = memberRepository.findByEmail(email);

        if (existingMember != null) {
            // 기존 회원
            return existingMember;
        } else {
            // 신규 회원
            Member newMember = new Member(name, email, provider, providerId, Role.USER);
            newMember.setPassword("");  // 패스워드 없음
            return memberRepository.save(newMember);
        }
    }

    // 새로운 비즈니스 로직: 노트 저장
    @Transactional
    public void saveNoteForMember(Long memberId, Long noteId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new IllegalArgumentException("Note not found"));

        member.getSavedNotes().add(note);  // 사용자 저장 목록에 노트를 추가
        memberRepository.save(member);
    }

    // 노트 저장 제거
    @Transactional
    public void removeSavedNoteForMember(Long memberId, Long noteId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new IllegalArgumentException("Note not found"));

        member.getSavedNotes().remove(note);  // 저장된 노트를 목록에서 제거
        memberRepository.save(member);
    }

    // 저장된 노트 조회
    @Transactional(readOnly = true)
    public Set<Note> getSavedNotes(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return member.getSavedNotes();  // 저장된 노트 목록 반환
    }
}
