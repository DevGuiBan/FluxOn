package com.guiban.fluxOn.domain.user.dto;

import com.guiban.fluxOn.domain.user.User;
import com.guiban.fluxOn.domain.user.UserRole;

import java.util.UUID;

public record UserResponseDTO(UUID id,
                              String name,
                              String email,
                              UserRole role,
                              boolean active) {

    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        );
    }
}
