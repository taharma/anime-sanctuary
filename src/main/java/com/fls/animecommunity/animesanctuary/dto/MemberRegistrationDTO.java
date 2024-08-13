package com.fls.animecommunity.animesanctuary.dto;

public class MemberRegistrationDTO {

    private String username;
    private String password;
    private String email;

    // 기본 생성자
    public MemberRegistrationDTO() {
    }

    // 필드별 getter 및 setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
