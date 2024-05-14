package com.example.authserver.application;

import com.example.authserver.adapter.in.JoinRequest;
import com.example.authserver.adapter.in.VerifyPhoneNumberRequest;
import com.example.authserver.application.port.in.JoinUseCase;
import com.example.authserver.application.port.out.external.SMSPort;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.Member;
import com.example.authserver.exception.VerificationFailException;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class JoinService implements JoinUseCase {

    private final MemberPort memberPort;
    private final SMSPort smsPort;
    private final RedisPort redisPort;
    private final Random random = new Random();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void join(JoinRequest joinRequest) {

        boolean isVerifiedRequest = redisPort
                .checkVerification(joinRequest.phoneNumber(), joinRequest.email());

        if(!isVerifiedRequest) {
            throw new VerificationFailException("인증되지 않은 이메일/핸드폰 번호 입니다.");
        }

        Member member = Member.newMemberFromRequest(joinRequest, passwordEncoder);

        memberPort.save(member);
    }

    @Override
    public boolean isDuplicateEmail(String email) {

        return memberPort.findByEmailAndDeletionYn(email, "N").isPresent();

    }

    @Override
    public String requestNumberVerification(VerifyPhoneNumberRequest request) {

        String verificationCode = String.valueOf(random.nextInt(900000) + 100000);

        smsPort.sendVerificationSMS();

        redisPort.saveVerificationCode(request.phoneNumber(), verificationCode);

        return verificationCode;
    }

    @Override
    public void verifyCode(String email, String phoneNumber, String verificationCode) {

        String storedCode = redisPort.getVerificationCode(phoneNumber)
                .orElseThrow(() -> new NotFoundException("인증 코드"));

        if(!storedCode.equals(verificationCode)) {
            throw new VerificationFailException("인증에 실패했습니다.");
        }

        redisPort.verifyNumber(phoneNumber, email);
    }
}
