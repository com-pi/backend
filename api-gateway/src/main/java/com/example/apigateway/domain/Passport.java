package com.example.apigateway.domain;

import jakarta.annotation.Nonnull;

public record Passport(
    Long memberId,
    Role role
) {
    public static Passport of(@Nonnull String id,
                              @Nonnull String role) {
        try {
            long memberId = Long.parseLong(id);
            Role memberRole = Role.of(role);
            return new Passport(memberId, memberRole);
        } catch (NumberFormatException e) {
            // 멤버 아이디 파싱 오류
            return null;
        } catch (IllegalArgumentException e) {
            // 멤버 권한 파싱 오류
            return null;
        }
    }

    public String toJson() {
        return String.format(
                "{\"memberId\":\"%s\"," +
                "\"role\":\"%s\"}",
                this.memberId, this.role
        );
    }

}
