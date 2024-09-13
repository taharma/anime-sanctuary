package com.fls.animecommunity.animesanctuary.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
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
    private final NoteRepository noteRepository;
    private final PasswordEncoder passwordEncoder;

    // MemberRepository와 NoteRepository, PasswordEncoder 주입
    public MemberService(MemberRepository memberRepository, NoteRepository noteRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.noteRepository = noteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 처리
    @Transactional
    public Member register(Member member) {
        // 이미 존재하는 username을 확인
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // 비밀번호가 null이거나 빈 값인 경우 에러 발생
        if (member.getPassword() == null || member.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // 비밀번호 암호화 후 저장
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    // 로그인 처리
    public Member login(String usernameOrEmail, String password) {
        // Username 또는 Email로 멤버 검색
        Member member = memberRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            return member;
        }
        return null; // 비밀번호 불일치 시 null 반환
    }

    // 회원 삭제 처리
    @Transactional
    public boolean deleteMember(Long id, String password) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            memberRepository.deleteById(id); // 비밀번호가 일치하면 삭제
            return true;
        }
        return false;
    }

    // 이메일로 멤버 찾기
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 특정 ID의 멤버 프로필 정보 가져오기
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

        // Optional을 사용하여 값이 있을 때만 업데이트 (null 체크 대신 사용)
        // Optional.ofNullable은 값이 null일 수도 있는 필드에 안전하게 접근하기 위한 방법으로, 코드의 가독성을 높이고 null 체크를 간결하게 만듭니다.
        Optional.ofNullable(updateRequest.getUsername()).ifPresent(existingMember::setUsername);
        Optional.ofNullable(updateRequest.getName()).ifPresent(existingMember::setName);
        Optional.ofNullable(updateRequest.getBirth()).ifPresent(existingMember::setBirth);
        Optional.ofNullable(updateRequest.getEmail()).ifPresent(existingMember::setEmail);
        Optional.ofNullable(updateRequest.getGender()).ifPresent(existingMember::setGender);

        // 비밀번호 업데이트 로직
        // 현재 비밀번호가 null이거나, 현재 비밀번호가 기존 비밀번호와 일치하지 않는 경우 에러를 발생시킨다.
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            // 현재 비밀번호가 일치하는지 확인
            if (updateRequest.getCurrentPassword() == null ||
                !passwordEncoder.matches(updateRequest.getCurrentPassword(), existingMember.getPassword())) {
                throw new IllegalArgumentException("Current password is incorrect");
            }
            // 새 비밀번호로 설정
            existingMember.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        return memberRepository.save(existingMember);
    }

    // 프로필 이미지 업로드
    public String uploadProfileImage(Long userId, MultipartFile image) throws IOException {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        String uploadDir = "uploads/profile-images/";
        Files.createDirectories(Paths.get(uploadDir)); // 디렉토리가 없을 시 생성

        String originalFilename = image.getOriginalFilename();
        String newFilename = UUID.randomUUID().toString() + "_" + originalFilename; // UUID로 파일 이름 중복 방지
        String imagePath = uploadDir + newFilename;

        File imageFile = new File(imagePath);
        image.transferTo(imageFile); // 이미지 저장

        member.setProfileImage(imagePath); // 프로필 이미지 경로 업데이트
        memberRepository.save(member);

        return imagePath;
    }

    // 프로필 이미지 경로 업데이트
    public void updateProfileImage(Long userId, String filePath) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        member.setProfileImage(filePath);
        memberRepository.save(member);
    }

    // 프로필 이미지 삭제
    public void deleteProfileImage(Long userId) throws IOException {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    
        // 파일 삭제 처리 (java.nio.file.Files의 delete 메서드를 사용하여 예외를 명확하게 처리)
        String filePath = member.getProfileImage();
        if (filePath != null && !filePath.isEmpty()) {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.delete(path); // 파일 삭제
            }
    
            member.setProfileImage(null); // 프로필 이미지 필드 초기화
            memberRepository.save(member);
        }
    }

    // 소셜 로그인 또는 회원가입 처리
    @Transactional
    public Member registerOrLoginWithSocial(String name, String email, String provider, String providerId) {
        Member existingMember = memberRepository.findByEmail(email);

        if (existingMember != null) {
            return existingMember; // 기존 회원이 있으면 반환
        } else {
            // 새로운 소셜 회원 가입 처리
            Member newMember = new Member(name, email, provider, providerId, Role.USER);
            newMember.setPassword(""); // 패스워드는 공백으로 설정
            return memberRepository.save(newMember);
        }
    }

    // 노트 저장 처리
    @Transactional
    public boolean saveNoteForUser(Long memberId, Long noteId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));

        // 해당 노트가 저장되어 있지 않으면 추가
        if (!member.getSavedNotes().contains(note)) {
            member.getSavedNotes().add(note);  // 'savedNotes' 컬렉션이 Member 클래스에 있다고 가정
            memberRepository.save(member);
            return true;
        }
        return false; // 이미 저장된 경우
    }
}
