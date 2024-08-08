package com.example.authserver.application;

import com.example.authserver.adapter.in.request.MemberCreateRequest;
import com.example.authserver.adapter.in.request.VerifyCodeForJoinRequest;
import com.example.authserver.adapter.in.request.VerifyPhoneNumberRequest;
import com.example.authserver.application.port.in.JoinUseCase;
import com.example.authserver.application.port.out.external.SlackWebhookClient;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.SlackMessage;
import com.example.authserver.exception.VerificationFailException;
import com.example.common.exception.ConflictException;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class JoinService implements JoinUseCase {

    private final MemberQuery memberQuery;
    private final MemberCommand memberCommand;
    private final SlackWebhookClient smsPort;
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

        Member newMember = Member.create(memberCreateRequest.toDomain(), passwordEncoder);
        // 참고로 UUID 8자리에 대해서는 42억명 정도의 회원을 담을 수 있고,
        // 7.7만명의 회원이 가입하면 중복될 확률이 50% 정도가 됩니다.
        while(true){
            try {
                memberCommand.save(newMember);
                break;
            } catch(DataIntegrityViolationException e) {
                newMember = newMember.changeNickname();
            }
        }
    }

    @Override
    public boolean checkEmailDuplication(String email) {
        return memberQuery.findByEmail(email).isPresent();
    }

    @Override
    public String requestNumberVerification(VerifyPhoneNumberRequest request) {
        if(checkEmailDuplication(request.email())){
            throw new ConflictException("이미 가입된 이메일 주소 입니다.");
        }
        Optional<Member> findByPhoneNumber = memberQuery.findByPhoneNumber(request.phoneNumber());
        if(findByPhoneNumber.isPresent()) {
            throw new ConflictException("이미 가입된 전화번호 입니다.");
        }

        String verificationCode = String.valueOf(random.nextInt(900000) + 100000);

        smsPort.sendSlackMessage(SlackMessage.of(verificationCode, request.phoneNumber()));
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
