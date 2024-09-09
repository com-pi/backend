package com.example.authserver.adapter.in.response;

import com.example.authserver.domain.ComppiToken;

public record TokenReIssueResponse(
        ComppiToken comppiToken,
        String message
) {
    public static TokenReIssueResponse of(ComppiToken comppiToken){
        return new TokenReIssueResponse(comppiToken, "엑세스 토큰이 재발급 되었습니다.");
    }
}
