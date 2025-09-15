package com.guiban.fluxOn.user.dto;

import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.user.UserRole;

import java.util.UUID;

public record UserResponseDTO(UUID id, String name, String email, UserRole role, boolean active) {

    public UserResponseDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.isActive());
    }
}
