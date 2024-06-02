package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.request.MemberCreateRequest;
import com.example.authserver.adapter.in.request.VerifyCodeForJoinRequest;
import com.example.authserver.adapter.in.request.VerifyPhoneNumberRequest;

public interface JoinUseCase {

    void join(MemberCreateRequest memberCreateRequest);
    boolean checkEmailDupliction(String email);
    String requestNumberVerification(VerifyPhoneNumberRequest request);
    void verifyCode(VerifyCodeForJoinRequest request);

}
