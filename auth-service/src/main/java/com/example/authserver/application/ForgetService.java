package com.example.authserver.application;

import com.example.authserver.adapter.in.FindIdRequest;
import com.example.authserver.adapter.in.FindPwdRequest;
import com.example.authserver.adapter.in.VerifyCodeForEmailRequest;
import com.example.authserver.application.port.out.external.EmailPort;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.Member;
import com.example.authserver.exception.BadRequestException;
import com.example.common.exception.NotFoundException;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForgetService {

    private final RedisPort redisPort;
    private final MemberPort memberPort;
    private final EmailPort emailPort;
    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String findId(FindIdRequest request){
        Member foundMember = memberPort.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException(Member.class));

        if(foundMember.isSocialAccount()) {
            throw new BadRequestException("소셜 로그인 계정입니다. 로셜 로그인을 이용해 주세요");
        }

        String verificationCode = String.valueOf(random.nextInt(900000) + 100000);

        redisPort.setFindIdValidationCode(request.phoneNumber(), verificationCode);

        return verificationCode;
    }

    @Nullable
    public String verifyCode(VerifyCodeForEmailRequest request){
        boolean isMatch = redisPort.verifyFindIdCode(request.phoneNumber(), request.verifyCode());

        if(isMatch) {
            Member foundMember = memberPort.findByPhoneNumber(request.phoneNumber())
                    .orElseThrow(() -> new NotFoundException(Member.class));
            return foundMember.getEmail();
        }

        return null;
    }

    @Transactional
    public void findPassword(FindPwdRequest request){
        Member member = memberPort.findByPhoneNumberAndEmailAndDeletionYn(
                request.phoneNumber(),
                request.email(),
                "N").orElseThrow(() -> new NotFoundException(Member.class));

        String tempPwd = UUID.randomUUID().toString().substring(0, 9);

        member.changePassword(tempPwd, passwordEncoder);

        emailPort.sendPasswordEmail(member.getEmail(), tempPwd);
    }



}
