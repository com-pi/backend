package com.example.common.domain;

public enum Role {
    ANONYMOUS(0),
    USER(1),
    ADMIN(2);

    final Integer rank;

    Role(int rank) {
        this.rank = rank;
    }

    public static Role of(String name) {
        for (Role role : Role.values()) {
            if(role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean hasPermission(Role role) {
        return this.rank >= role.rank;
    }


}
