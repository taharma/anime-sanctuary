package com.fls.animecommunity.animesanctuary.dto;

import lombok.Data;

@Data
public class LoginRequest {
	private String usernameOrEmail;
    private String password;
}
