package com.example.common.domain;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public record Passport(
    Long memberId,
    Role role,
    String nickName,
    String thumbnail
) {
    public static Passport of(@Nonnull String id,
                              @Nonnull String role,
                              @Nonnull String nickName,
                              @Nullable String thumbnail) {
        try {
            long memberId = Long.parseLong(id);
            Role memberRole = Role.of(role);
            return new Passport(memberId, memberRole, nickName, thumbnail);
        } catch (NumberFormatException e) {
            // 멤버 아이디 파싱 오류
            return null;
        } catch (IllegalArgumentException e) {
            // 멤버 권한 파싱 오류
            return null;
        }
    }

    public static Passport anonymous(){
        return new Passport(0L, Role.ANONYMOUS, "", "");
    }

}
