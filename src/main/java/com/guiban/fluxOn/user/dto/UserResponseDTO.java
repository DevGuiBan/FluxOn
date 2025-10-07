package com.guiban.fluxOn.user.dto;

import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.user.UserRole;

public record UserResponseDTO(String name,
                              String email,
                              UserRole role,
                              boolean active) {

    public UserResponseDTO(User user) {
        this(
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        );
    }
}
