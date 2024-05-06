package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.JoinRequest;

public interface JoinUseCase {

    void join(JoinRequest joinRequest);

}
