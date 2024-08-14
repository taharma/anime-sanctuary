package com.fls.animecommunity.animesanctuary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.model.Member;
import com.fls.animecommunity.animesanctuary.model.Scrap;
import com.fls.animecommunity.animesanctuary.service.MemberService;

@RestController
@RequestMapping("/api/users")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<Member> getProfile(@PathVariable("userId") Long userId) {
        Member member = memberService.getProfile(userId);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // 프로필 수정
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateProfile(
        @PathVariable("userId") Long userId,
        @RequestBody Member updatedMember,
        @RequestParam("currentPassword") String currentPassword
    ) {
        try {
            Member member = memberService.updateProfile(userId, updatedMember, currentPassword);
            if (member != null) {
                return ResponseEntity.ok(member);
            } else {
                return ResponseEntity.status(404).build();
            }
        } catch (IllegalArgumentException e) {
            // String으로 반환하기 위해 ResponseEntity<String>으로 변경
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }


    // 스크랩 목록 조회
    @GetMapping("/{userId}/scraps")
    public ResponseEntity<List<Scrap>> getScraps(@PathVariable("userId") Long userId) {
        List<Scrap> scraps = memberService.getScraps(userId);
        return ResponseEntity.ok(scraps);
    }
}
