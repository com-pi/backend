package com.example.authserver.util;

import com.example.authserver.domain.ComPToken;

public record AuthenticateResponse(
        ComPToken accessToken,
        ComPToken refreshToken,
        Boolean isNewMember
) {
}
