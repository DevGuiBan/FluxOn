package com.guiban.fluxOn.domain.user;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("admin"),
    EMPLOYEE("employee"),
    CLIENT("client");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
