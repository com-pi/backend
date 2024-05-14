package com.example.authserver.application;

import com.example.authserver.adapter.in.FindIdRequest;
import com.example.authserver.adapter.in.VerifyCodeRequest;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.Member;
import com.example.common.exception.NotFoundException;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgetService {

    private final RedisPort redisPort;
    private final MemberPort memberPort;
    private final Random random = new Random();

    public String findId(FindIdRequest request){
        memberPort.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException(Member.class));

        String verificationCode = String.valueOf(random.nextInt(900000) + 100000);

        redisPort.setFindIdValidationCode(request.phoneNumber(), verificationCode);

        return verificationCode;
    }

    @Nullable
    public String verifyCode(VerifyCodeRequest request){
        boolean isMatch = redisPort.verifyFindIdCode(request.phoneNumber(), request.verifyCode());

        if(isMatch) {
            Member foundMember = memberPort.findByPhoneNumber(request.phoneNumber())
                    .orElseThrow(() -> new NotFoundException(Member.class));
            return foundMember.getPhoneNumber();
        }

        return null;
    }



}
