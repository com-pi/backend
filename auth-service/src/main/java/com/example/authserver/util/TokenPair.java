package com.example.authserver.util;

import com.example.authserver.domain.ComppiToken;

public record TokenPair(
        ComppiToken accessToken,
        ComppiToken refreshToken
) {
}
