package com.fls.animecommunity.animesanctuary.dto;

import com.fls.animecommunity.animesanctuary.model.User;

public class UserProfileDTO {
    private String name;
    private String email;
    private String username;

    public UserProfileDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

    // Getters and Setters
}
