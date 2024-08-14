package com.fls.animecommunity.animesanctuary.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fls.animecommunity.animesanctuary.dto.ProfileUpdateDTO;
import com.fls.animecommunity.animesanctuary.dto.UserProfileDTO;
import com.fls.animecommunity.animesanctuary.model.User;
import com.fls.animecommunity.animesanctuary.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserProfileDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileDTO(user);
    }

    public UserProfileDTO updateUserProfile(Long userId, ProfileUpdateDTO profileUpdateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (profileUpdateDTO.getPassword() != null && !profileUpdateDTO.getPassword().isEmpty()) {
            if (passwordEncoder.matches(profileUpdateDTO.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(profileUpdateDTO.getPassword()));
            } else {
                throw new RuntimeException("Current password is incorrect");
            }
        }

        user.setEmail(profileUpdateDTO.getEmail());
        user.setName(profileUpdateDTO.getName());
        userRepository.save(user);

        return new UserProfileDTO(user);
    }

    public List<Object> getUserScraps(Long userId) {
        // 사용자 스크랩 목록을 가져오는 로직을 여기에 추가합니다.
        // 예시: scrapRepository.findByUserId(userId)
        return List.of(); // 예시 리턴 값
    }
}
