package com.example.common.domain;

public record Passport(
    Long memberId,
    Role role
) {
    public static Passport of(String id,
                              String role) {
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
    public static Passport anonymous(){
        return new Passport(0L, Role.ANONYMOUS);
    }

}
