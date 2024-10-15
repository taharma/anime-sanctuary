package com.fls.animecommunity.animesanctuary.dto;

public class LoginResponse {
    private String message;
    private Long userId;

    // 생성자
    public LoginResponse(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

    // Getter와 Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
