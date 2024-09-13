package com.fls.animecommunity.animesanctuary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fls.animecommunity.animesanctuary.model.UpdateProfileRequest;
import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.repository.MemberRepository;
import com.fls.animecommunity.animesanctuary.service.impl.MemberService;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Mock 초기화
    }

    // register #1
    @Test
    void testRegisterNewMember() {
        // Given
        Member member = new Member();
        member.setUsername("testuser");
        member.setPassword("password123");

        when(memberRepository.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // When
        Member registeredMember = memberService.register(member);

        // Then
        assertNotNull(registeredMember);
        assertEquals("testuser", registeredMember.getUsername());
        assertEquals("encodedPassword", registeredMember.getPassword());
    }

    // Duplicated checker
    @Test
    void testRegisterExistingMember() {
        // Given
        Member member = new Member();
        member.setUsername("existinguser");
        member.setPassword("password123");

        when(memberRepository.existsByUsername("existinguser")).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.register(member);
        });

        assertEquals("Username already exists", exception.getMessage());
    }


    @Test
    void testLoginSuccess() {
        // Given
        String username = "testuser";
        String password = "password123";
        Member member = new Member();
        member.setUsername(username);
        member.setPassword("encodedPassword");

        when(memberRepository.findByUsernameOrEmail(username, username)).thenReturn(member);
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(true);

        // When
        Member loggedInMember = memberService.login(username, password);

        // Then
        assertNotNull(loggedInMember);
        assertEquals(username, loggedInMember.getUsername());
    }

    @Test
    void testLoginFailure() {
        // Given
        String username = "testuser";
        String password = "wrongPassword";
        Member member = new Member();
        member.setUsername(username);
        member.setPassword("encodedPassword");

        when(memberRepository.findByUsernameOrEmail(username, username)).thenReturn(member);
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(false);

        // When
        Member loggedInMember = memberService.login(username, password);

        // Then
        assertNull(loggedInMember);
    }

    // 프로필 업데이트 테스트 - 성공
    @Test
    void testUpdateProfileSuccess() {
        // Given
        Long memberId = 1L;
        UpdateProfileRequest updateRequest = new UpdateProfileRequest();
        updateRequest.setUsername("newUsername");
        updateRequest.setPassword("newPassword");
        updateRequest.setCurrentPassword("oldPassword");

        Member existingMember = new Member();
        existingMember.setUsername("oldUsername");
        existingMember.setPassword("encodedOldPassword");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));
        when(passwordEncoder.matches("oldPassword", "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(memberRepository.save(existingMember)).thenReturn(existingMember);

        // When
        Member updatedMember = memberService.updateProfile(memberId, updateRequest);

        // Then
        assertNotNull(updatedMember);
        assertEquals("newUsername", updatedMember.getUsername());
        assertEquals("encodedNewPassword", updatedMember.getPassword());
    }

    // 프로필 업데이트 테스트 - 비밀번호 불일치
    @Test
    void testUpdateProfileInvalidCurrentPassword() {
        // Given
        Long memberId = 1L;
        UpdateProfileRequest updateRequest = new UpdateProfileRequest();
        updateRequest.setPassword("newPassword");
        updateRequest.setCurrentPassword("wrongOldPassword");

        Member existingMember = new Member();
        existingMember.setPassword("encodedOldPassword");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));
        when(passwordEncoder.matches("wrongOldPassword", "encodedOldPassword")).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.updateProfile(memberId, updateRequest);
        });

        assertEquals("Current password is incorrect", exception.getMessage());
    }

        // 소셜 회원가입/로그인 테스트 - 기존 회원
        @Test
        void testRegisterOrLoginWithSocialExistingMember() {
            // Given
            String name = "Test User";
            String email = "test@example.com";
            String provider = "google";
            String providerId = "12345";
    
            Member existingMember = new Member();
            existingMember.setEmail(email);
    
            when(memberRepository.findByEmail(email)).thenReturn(existingMember);
    
            // When
            Member member = memberService.registerOrLoginWithSocial(name, email, provider, providerId);
    
            // Then
            assertNotNull(member);
            assertEquals(email, member.getEmail());
        }
    
        // 소셜 회원가입/로그인 테스트 - 신규 회원
        @Test
        void testRegisterOrLoginWithSocialNewMember() {
            // Given
            String name = "New User";
            String email = "new@example.com";
            String provider = "google";
            String providerId = "67890";
    
            when(memberRepository.findByEmail(email)).thenReturn(null);
            when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
            // When
            Member member = memberService.registerOrLoginWithSocial(name, email, provider, providerId);
    
            // Then
            assertNotNull(member);
            assertEquals(email, member.getEmail());
            assertEquals("", member.getPassword());  // 소셜 로그인 시 비밀번호는 빈 문자열로 설정됨
        }
}
