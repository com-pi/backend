package com.example.authserver.application.port.in;

import com.example.authserver.domain.ComPToken;

public record LoginResponse(
        ComPToken comPToken,
        Boolean isNewMember
) {
    public static LoginResponse of(ComPToken comPToken, Boolean isNewMember){
        return new LoginResponse(comPToken, isNewMember);
    }
}
