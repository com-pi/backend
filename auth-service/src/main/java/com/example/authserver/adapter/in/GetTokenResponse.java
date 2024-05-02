package com.example.authserver.adapter.in;

public record GetTokenResponse(
        String accessToken,
        Boolean isNewMember
) {}
