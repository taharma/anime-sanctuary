package com.fls.animecommunity.animesanctuary.dto.memberDto;

import lombok.Data;

@Data
public class LoginRequest {
	private String usernameOrEmail;
    private String password;
}
