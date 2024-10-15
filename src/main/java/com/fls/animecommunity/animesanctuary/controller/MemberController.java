package com.fls.animecommunity.animesanctuary.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fls.animecommunity.animesanctuary.dto.MemberRegisterDto;
import com.fls.animecommunity.animesanctuary.model.UpdateProfileRequest;
import com.fls.animecommunity.animesanctuary.model.member.GenderType;
import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.service.impl.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody MemberRegisterDto memberDto, BindingResult result) {
	    log.info("call MemberController register()");
	    log.info("회원가입 요청 도착: /api/members/register");
	    log.info("memberDto : {}", memberDto);
	    
	    if (result.hasErrors()) {
	        StringBuilder errorMessage = new StringBuilder();
	        result.getFieldErrors().forEach(error -> {
	            errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
	        });
	        return ResponseEntity.badRequest().body(errorMessage.toString());
	    }

	    Member member = new Member();
	    member.setUsername(memberDto.getUsername());
	    member.setPassword(memberDto.getPassword());  // 비밀번호는 해시 처리 필요
	    member.setName(memberDto.getName());
	    member.setEmail(memberDto.getEmail());

	    try {
	        member.setGender(GenderType.valueOf(memberDto.getGender().toUpperCase()));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("Invalid gender value: " + memberDto.getGender());
	    }

	    member.setBirth(memberDto.getBirth());
	    Member registeredMember = memberService.register(member);
	    return ResponseEntity.ok(registeredMember);
	}

	// logout 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return ResponseEntity.ok("Logout successful");
	}

	// deleteMember 회원삭제
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id, @RequestParam("username") String username,
											@RequestParam("password") String password, HttpServletRequest request) {
		Member loggedInMember = (Member) request.getSession().getAttribute("user");
		if (loggedInMember == null) {
			return ResponseEntity.status(403).build(); // 로그인하지 않은 경우, Forbidden
		}

		boolean isDeleted = memberService.deleteMember(id, password);
		if (isDeleted) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(401).build();
		}
	}

	@PostMapping("/updateProfile/{userId}")
	public ResponseEntity<?> updateProfile(@PathVariable("userId") Long userId,
			@RequestBody UpdateProfileRequest updateRequest) {
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

	@GetMapping("/profile/{id}")
	public ResponseEntity<Member> getProfile(@PathVariable("id") Long id) {
		Member member = memberService.getProfile(id);
		if (member != null) {
			return ResponseEntity.ok(member);
		} else {
			return ResponseEntity.status(404).build();
		}
	}

	@PostMapping("/{userId}/uploadProfileImage")
	public ResponseEntity<String> uploadProfileImage(@PathVariable("userId") Long userId,
			@RequestParam("image") MultipartFile image) {
		try {
			String uploadDir = Paths.get(System.getProperty("user.dir"), "uploads", "profile-images", userId.toString())
					.toString();
			File directory = new File(uploadDir);
			if (!directory.exists() && !directory.mkdirs()) {
				throw new IOException("Failed to create directory: " + uploadDir);
			}

			String originalFilename = image.getOriginalFilename();
			String filePath = Paths.get(uploadDir, UUID.randomUUID().toString() + "_" + originalFilename).toString();

			File destinationFile = new File(filePath);
			image.transferTo(destinationFile);

			memberService.updateProfileImage(userId, filePath);

			return ResponseEntity.ok(filePath);
		} catch (IOException | IllegalStateException e) {
			return ResponseEntity.status(500).body("File I/O error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(400).body("Error: " + e.getMessage());
		}
	}

	@DeleteMapping("/{userId}/deleteProfileImage")
	public ResponseEntity<String> deleteProfileImage(@PathVariable("userId") Long userId) {
		try {
			memberService.deleteProfileImage(userId);
			return ResponseEntity.ok("Profile image deleted successfully");
		} catch (IOException e) {
			return ResponseEntity.status(500).body("File I/O error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(400).body("Error: " + e.getMessage());
		}
	}

	@GetMapping("/findByEmail")
	public ResponseEntity<Member> findByEmail(@RequestParam String email) {
		Optional<Member> member = memberService.findByEmail(email);
		if (member.isPresent()) {
			return ResponseEntity.ok(member.get());
		} else {
			return ResponseEntity.status(404).build();
		}
	}
}
