package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.JoinRequest;
import com.example.authserver.adapter.in.VerifyPhoneNumberRequest;

public interface JoinUseCase {

    void join(JoinRequest joinRequest);
    boolean isDuplicateEmail(String email);
    String requestNumberVerification(VerifyPhoneNumberRequest request);
    void verifyCode(String email, String phoneNumber, String verificationCode);

}
