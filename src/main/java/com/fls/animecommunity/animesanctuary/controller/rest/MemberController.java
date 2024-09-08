package com.fls.animecommunity.animesanctuary.controller.rest;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fls.animecommunity.animesanctuary.model.UpdateProfileRequest;
import com.fls.animecommunity.animesanctuary.model.member.GenderType;
import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.service.impl.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fls.animecommunity.animesanctuary.dto.LoginRequest;
import com.fls.animecommunity.animesanctuary.dto.MemberRegisterDto;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "http://localhost:9000")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Member> register(@RequestBody MemberRegisterDto memberDto) {
        Member member = new Member();
        member.setUsername(memberDto.getUsername());
        member.setPassword(memberDto.getPassword());
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setGender(GenderType.valueOf(memberDto.getGender().toUpperCase()));
        member.setBirth(memberDto.getBirth());

        Member registeredMember = memberService.register(member);
        return ResponseEntity.ok(registeredMember);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        Member member = memberService.login(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
        if (member != null) {
            request.getSession().setAttribute("user", member);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username/email or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logout successful");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id, @RequestParam("password") String password, HttpServletRequest request) {
        Member loggedInMember = (Member) request.getSession().getAttribute("user");
        if (loggedInMember == null) {
            return ResponseEntity.status(403).build(); // 로그인하지 않은 경우
        }
        boolean isDeleted = memberService.deleteMember(id, password);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Member> getProfile(@PathVariable("id") Long id) {
        Member member = memberService.getProfile(id);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/updateProfile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable("userId") Long userId, @RequestBody UpdateProfileRequest updateRequest) {
        try {
            Member member = memberService.updateProfile(userId, updateRequest);
            if (member != null) {
                return ResponseEntity.ok(member);
            } else {
                return ResponseEntity.status(404).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/{userId}/uploadProfileImage")
    public ResponseEntity<?> uploadProfileImage(@PathVariable("userId") Long userId, @RequestParam("image") MultipartFile image) {
        try {
            String imagePath = memberService.uploadProfileImage(userId, image);
            return ResponseEntity.ok(imagePath);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/deleteProfileImage")
    public ResponseEntity<?> deleteProfileImage(@PathVariable("userId") Long userId) {
        try {
            memberService.deleteProfileImage(userId);
            return ResponseEntity.ok("Profile image deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<Member> findByEmail(@RequestParam String email) {
        Member member = memberService.findByEmail(email);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // 노트 저장 API
    @PostMapping("/{memberId}/saveNote/{noteId}")
    public ResponseEntity<?> saveNote(@PathVariable Long memberId, @PathVariable Long noteId) {
        memberService.saveNoteForMember(memberId, noteId);
        return ResponseEntity.ok("Note saved successfully");
    }

    // 저장된 노트 삭제 API
    @DeleteMapping("/{memberId}/removeSavedNote/{noteId}")
    public ResponseEntity<?> removeSavedNote(@PathVariable Long memberId, @PathVariable Long noteId) {
        memberService.removeSavedNoteForMember(memberId, noteId);
        return ResponseEntity.ok("Saved note removed successfully");
    }

    // 저장된 노트 목록 조회 API
    @GetMapping("/{memberId}/savedNotes")
    public ResponseEntity<Set<NoteResponseDto>> getSavedNotes(@PathVariable Long memberId) {
        Set<Note> savedNotes = memberService.getSavedNotes(memberId);
        Set<NoteResponseDto> savedNotesDto = savedNotes.stream()
            .map(NoteResponseDto::new)
            .collect(Collectors.toSet());
        return ResponseEntity.ok(savedNotesDto);
    }
}
