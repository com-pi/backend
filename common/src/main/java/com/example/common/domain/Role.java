package com.example.common.domain;

public enum Role {
    USER, ADMIN;

    public static Role of(String name) {
        for (Role role : Role.values()) {
            if(role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException();
    }
}
