package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fls.animecommunity.animesanctuary.dto.ProfileUpdateDTO;
import com.fls.animecommunity.animesanctuary.dto.UserProfileDTO;
import com.fls.animecommunity.animesanctuary.model.User;
import com.fls.animecommunity.animesanctuary.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long userId) {
        UserProfileDTO userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }

    // 프로필 수정
    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> updateUserProfile(@PathVariable Long userId, @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        UserProfileDTO updatedProfile = userService.updateUserProfile(userId, profileUpdateDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    // 스크랩 목록 조회
    @GetMapping("/{userId}/scraps")
    public ResponseEntity<List<Object>> getUserScraps(@PathVariable Long userId) {
        List<Object> scraps = userService.getUserScraps(userId);
        return ResponseEntity.ok(scraps);
    }
}
