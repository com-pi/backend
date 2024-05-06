package com.example.apigateway.domain;

public record Passport(
    Long memberId,
    Role role
) {

    public static Passport of(String id, String role) {
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
                "{\"memberId\":\"%s\",\"role\":\"%s\"}", this.memberId, this.role
        );
    }

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

}
