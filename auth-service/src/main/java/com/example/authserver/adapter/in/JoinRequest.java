package com.example.authserver.adapter.in;


public record JoinRequest(
        String email,
        String password,
        String phoneNumber
) {}
