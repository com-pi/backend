package com.example.authserver.adapter.in.response;

import com.example.authserver.domain.ComPToken;

public record TokenReIssueResponse(
        ComPToken comPToken,
        String message
) {
    public static TokenReIssueResponse of(ComPToken comPToken){
        return new TokenReIssueResponse(comPToken, "엑세스 토큰이 재발급 되었습니다.");
    }
}