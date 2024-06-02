package com.example.authserver.application;

import com.example.authserver.adapter.in.request.*;
import com.example.authserver.adapter.out.MemberEntity;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.application.util.JwtUtil;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.TokenType;
import com.example.authserver.exception.BadRequestException;
import com.example.authserver.exception.InvalidTokenException;
import com.example.common.domain.Passport;
import com.example.common.exception.NotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Builder
public class ForgetService {

    private final RedisPort redisPort;
    private final MemberQuery memberQuery;
    private final MemberCommand memberCommand;
    private final JwtUtil jwtUtil;
    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String findId(FindIdRequest request){
        Member foundMember = memberQuery.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        if(foundMember.isSocialAccount()) {
            throw new BadRequestException("소셜 로그인 계정입니다. 로셜 로그인을 이용해 주세요");
        }

        String verificationCode = String.valueOf(random.nextInt(900000) + 100000);
        redisPort.setFindIdValidationCode(request.phoneNumber(), verificationCode);

        return verificationCode;
    }

    public String verifyFindIdCode(VerifyCodeForEmailRequest request){
        boolean isMatch = redisPort.verifyFindIdCode(request.phoneNumber(), request.verifyCode());

        if(!isMatch){
            throw new BadRequestException("올바른 인증코드가 아닙니다.");
        }

        Member foundMember = memberQuery.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));
        return foundMember.getEmail();
    }

    public String findPassword(FindPasswordRequest request){
        memberQuery.findByPhoneNumberAndEmail(
                request.phoneNumber(),
                request.email())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        String verificationCode = String.valueOf(random.nextInt(900000) + 100000);

        redisPort.setFindPasswordValidationCode(
                request.phoneNumber(),
                request.email(),
                verificationCode
        );

        return verificationCode;
    }

    public ComPToken verifyPasswordCode(VerifyFindPasswordCodeRequest request){
        boolean isMatch = redisPort.verifyFindPasswordValidationCode(
                request.phoneNumber(),
                request.email(),
                request.verificationCode());

        if(!isMatch) {
            throw new BadRequestException("올바른 인증코드가 아닙니다.");
        }

        Member member = memberQuery.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        ComPToken passwordChangeToken = jwtUtil.generateToken(member, TokenType.PASSWORD_CHANGE_TOKEN);
        redisPort.setChangePasswordCode(request.phoneNumber(), request.email(), passwordChangeToken);

        return passwordChangeToken;
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        Passport passport = jwtUtil.validateToken(request.changePasswordToken(), TokenType.PASSWORD_CHANGE_TOKEN)
                .orElseThrow(() -> new InvalidTokenException(TokenType.PASSWORD_CHANGE_TOKEN));

        Member member = memberQuery.findById(passport.memberId())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        boolean isMatch = redisPort.verifyChangePasswordToken(
                member.getPhoneNumber(),
                member.getEmail(),
                request.changePasswordToken());

        if(!isMatch){
            throw new RuntimeException(TokenType.PASSWORD_CHANGE_TOKEN.getInvalidMessage());
        }

        Member modifiedMember = member.changePassword(request.changePasswordToken(), passwordEncoder);
        memberCommand.update(modifiedMember);
    }

}
