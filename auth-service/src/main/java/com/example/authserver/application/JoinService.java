package com.example.authserver.application;

import com.example.authserver.adapter.in.request.MemberCreateRequest;
import com.example.authserver.adapter.in.request.VerifyCodeForJoinRequest;
import com.example.authserver.adapter.in.request.VerifyPhoneNumberRequest;
import com.example.authserver.application.port.in.JoinUseCase;
import com.example.authserver.application.port.out.external.SMSPort;
import com.example.authserver.adapter.out.MemberJpaRepository;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.adapter.out.MemberEntity;
import com.example.authserver.domain.Member;
import com.example.authserver.exception.VerificationFailException;
import com.example.common.exception.ConflictException;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class JoinService implements JoinUseCase {

    private final MemberPort memberPort;
    private final SMSPort smsPort;
    private final RedisPort redisPort;
    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void join(MemberCreateRequest memberCreateRequest) {
        boolean isVerifiedRequest = redisPort
                .checkVerification(memberCreateRequest.phoneNumber(), memberCreateRequest.email());

        if(!isVerifiedRequest) {
            throw new VerificationFailException("인증되지 않은 이메일/핸드폰 번호 입니다.");
        }

        Member newMember = Member.create(memberCreateRequest.toDomain(passwordEncoder));
        memberPort.save(newMember);
    }

    @Override
    public boolean checkEmailDupliction(String email) {
        return memberPort.findByEmail(email).isPresent();
    }

    @Override
    public String requestNumberVerification(VerifyPhoneNumberRequest request) {
        if(checkEmailDupliction(request.email())){
            throw new ConflictException("이미 가입된 이메일 주소 입니다.");
        }
        Optional<Member> findByPhoneNumber = memberPort.findByPhoneNumber(request.phoneNumber());
        if(findByPhoneNumber.isPresent()) {
            throw new ConflictException("이미 가입된 전화번호 입니다.");
        }

        String verificationCode = String.valueOf(random.nextInt(900000) + 100000);

        smsPort.sendVerificationSMS();
        redisPort.saveVerificationCode(request.phoneNumber(), verificationCode);

        return verificationCode;
    }

    @Override
    public void verifyCode(VerifyCodeForJoinRequest request) {
        String storedCode = redisPort.getVerificationCode(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException("인증 코드"));

        if(!storedCode.equals(request.verificationCode())) {
            throw new VerificationFailException("인증에 실패했습니다.");
        }

        redisPort.verifyNumber(request.phoneNumber(), request.email());
    }
}
