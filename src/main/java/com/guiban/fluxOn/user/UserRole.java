package com.guiban.fluxOn.user;

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
