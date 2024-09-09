package com.example.authserver.application;

import com.example.authserver.adapter.in.request.*;
import com.example.authserver.application.port.out.external.SlackWebhookClient;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.application.util.JwtUtil;
import com.example.authserver.domain.ComppiToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.SlackMessage;
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
    private final SlackWebhookClient smsPort;

    public String findId(FindIdRequest request){
        Member foundMember = memberQuery.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException(Member.class));

        if(foundMember.isSocialAccount()) {
            throw new BadRequestException("소셜 로그인 계정입니다. 로셜 로그인을 이용해 주세요");
        }

        String verificationCode = String.valueOf(random.nextInt(900000) + 100000);
        redisPort.setFindIdValidationCode(request.phoneNumber(), verificationCode);
        smsPort.sendSlackMessage(SlackMessage.forgetIdMessage(verificationCode, request.phoneNumber()));

        return verificationCode;
    }

    public String verifyFindIdCode(VerifyCodeForEmailRequest request){
        boolean isMatch = redisPort.verifyFindIdCode(request.phoneNumber(), request.verifyCode());

        if(!isMatch){
            throw new BadRequestException("올바른 인증코드가 아닙니다.");
        }

        Member foundMember = memberQuery.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException(Member.class));
        return foundMember.getEmail();
    }

    public String findPassword(FindPasswordRequest request){
        memberQuery.findByPhoneNumberAndEmail(
                request.phoneNumber(),
                request.email())
                .orElseThrow(() -> new NotFoundException(Member.class));

        String verificationCode = String.valueOf(random.nextInt(900000) + 100000);

        redisPort.setFindPasswordValidationCode(
                request.phoneNumber(),
                request.email(),
                verificationCode
        );

        smsPort.sendSlackMessage(SlackMessage.findPasswordMessage(verificationCode, request.phoneNumber()));

        return verificationCode;
    }

    public ComppiToken verifyPasswordCode(VerifyFindPasswordCodeRequest request){
        boolean isMatch = redisPort.verifyFindPasswordValidationCode(
                request.phoneNumber(),
                request.email(),
                request.verificationCode());

        if(!isMatch) {
            throw new BadRequestException("올바른 인증코드가 아닙니다.");
        }

        Member member = memberQuery.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException(Member.class));

        ComppiToken passwordChangeToken = jwtUtil.generateToken(member, TokenType.PASSWORD_CHANGE_TOKEN);
        redisPort.setChangePasswordCode(request.phoneNumber(), request.email(), passwordChangeToken);

        return passwordChangeToken;
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        Passport passport = jwtUtil.validateToken(request.changePasswordToken(), TokenType.PASSWORD_CHANGE_TOKEN)
                .orElseThrow(() -> new InvalidTokenException("패스워드 변경을 위한 토큰이 유효하지 않습니다.", TokenType.PASSWORD_CHANGE_TOKEN));

        Member member = memberQuery.findById(passport.memberId())
                .orElseThrow(() -> new NotFoundException(Member.class));

        boolean isMatch = redisPort.verifyChangePasswordToken(
                member.getPhoneNumber(),
                member.getEmail(),
                request.changePasswordToken());

        if(!isMatch){
            throw new InvalidTokenException("패스워드 변경을 위한 토큰이 유효하지 않습니다.", TokenType.PASSWORD_CHANGE_TOKEN);
        }

        Member modifiedMember = member.changePassword(request.changePasswordToken(), passwordEncoder);
        memberCommand.update(modifiedMember);
    }

}
