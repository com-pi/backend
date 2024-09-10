package com.example.authserver.application;

import com.example.authserver.adapter.in.request.*;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.application.util.JwtUtil;
import com.example.authserver.domain.ComppiToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.TokenType;
import com.example.authserver.exception.BadRequestException;
import com.example.common.domain.Passport;
import com.example.common.domain.Role;
import com.example.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

class ForgetServiceTest {

    private MemberQuery memberQuery;
    private MemberCommand memberCommand;
    private RedisPort redisPort;
    private JwtUtil jwtUtil;
    private ForgetService forgetService;


    @BeforeEach
    void init() {
        memberQuery = mock(MemberQuery.class);
        memberCommand = mock(MemberCommand.class);
        redisPort = mock(RedisPort.class);
        jwtUtil = mock(JwtUtil.class);
        forgetService = ForgetService.builder()
                .redisPort(redisPort)
                .memberQuery(memberQuery)
                .memberCommand(memberCommand)
                .jwtUtil(jwtUtil)
                .build();
    }

    @Test
    void findId_아이디_찾기_요청시_인증코드를_반환합니다(){
        // given
        Member member = Member.builder()
                .id(1L)
                .phoneNumber("01012345678")
                .kakaoId(null)
                .naverId(null)
                .build();

        // when
        given(memberQuery.findByPhoneNumber("01012345678"))
                .willReturn(Optional.ofNullable(member));
        FindIdRequest request = new FindIdRequest("01012345678");
        String code = forgetService.findId(request);

        System.out.println(code);

        // given
        assertThat(code).isNotNull();
    }

    @Test
    void findId_회원이_소셜_로그인_계정이면_예외를_반환합니다(){
        // given
        Member member = Member.builder()
                .id(1L)
                .naverId("naver")
                .phoneNumber("01012345678")
                .build();

        given(memberQuery.findByPhoneNumber("01012345678"))
                .willReturn(Optional.ofNullable(member));

        FindIdRequest request = new FindIdRequest("01012345678");
        // when
        // then
        assertThatThrownBy(() -> forgetService.findId(request))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void verifyFindIdCode_올바른_코드를_입력하면_이메일_주소를_반환합니다(){
        // given
        VerifyCodeForEmailRequest request = VerifyCodeForEmailRequest.builder()
                .phoneNumber("01012345678")
                .verifyCode("123456")
                .build();

        Member member = Member.builder()
                .id(1L)
                .phoneNumber("01012345678")
                .email("kihong@naver.com")
                .build();

        // when
        given(redisPort.verifyFindIdCode(request.phoneNumber(), request.verifyCode()))
                .willReturn(true);
        given(memberQuery.findByPhoneNumber(request.phoneNumber())).willReturn(Optional.of(member));
        String email = forgetService.verifyFindIdCode(request);

        // then
        assertThat(email).isEqualTo(member.getEmail());
    }

    @Test
    void verifyFindIdCode_틀린_코드를_입력하면_예외를_던집니다(){
        // given
        VerifyCodeForEmailRequest request = VerifyCodeForEmailRequest.builder()
                .phoneNumber("01012345678")
                .verifyCode("123456")
                .build();

        Member member = Member.builder()
                .id(1L)
                .phoneNumber("01012345678")
                .email("kihong@naver.com")
                .build();

        // when
        given(redisPort.verifyFindIdCode(request.phoneNumber(), request.verifyCode()))
                .willReturn(false);
        given(memberQuery.findByPhoneNumber(request.phoneNumber())).willReturn(Optional.of(member));

        // then
        assertThatThrownBy(() -> forgetService.verifyFindIdCode(request))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void findPassword_올바른_이메일_핸드폰번호로_요청하면_인증번호를_반환합니다(){
        // given
        FindPasswordRequest request = FindPasswordRequest.builder()
                .email("kihong@naver.com")
                .phoneNumber("01012345678")
                .build();

        Member member = Member.builder()
                .id(1L)
                .email("kihong@naver.com")
                .phoneNumber("01012345678")
                .build();

        // when
        given(memberQuery.findByPhoneNumberAndEmail(request.phoneNumber(), request.email()))
                .willReturn(Optional.of(member));
        String code = forgetService.findPassword(request);

        // then
        assertThat(code).isNotBlank();
    }

    @Test
    void findPassword_올바른_이메일_핸드폰번호로_요청하면_예외를_던집니다(){
        // given
        FindPasswordRequest request = FindPasswordRequest.builder()
                .email("kihong@naver.com")
                .phoneNumber("01012345678")
                .build();

        // when
        given(memberQuery.findByPhoneNumberAndEmail(request.phoneNumber(), request.email()))
                .willReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> forgetService.findPassword(request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void verifyPasswordCode_올바른_인증코드로_요청하면_패스워드_변경_토큰을_반환합니다() {
        // given
        VerifyFindPasswordCodeRequest request = VerifyFindPasswordCodeRequest.builder()
                .email("kihong@naver.com")
                .phoneNumber("01012345678")
                .verificationCode("123456")
                .build();

        Member member = Member.builder()
                .id(1L)
                .email("kihong@naver.com")
                .phoneNumber("01012345678")
                .build();

        ComppiToken comppiToken = ComppiToken.of(TokenType.PASSWORD_CHANGE_TOKEN, "token");

        given(redisPort.verifyFindPasswordValidationCode(
                request.phoneNumber(),
                request.email(),
                request.verificationCode()
        )).willReturn(true);

        given(memberQuery.findByPhoneNumber(request.phoneNumber()))
                .willReturn(Optional.of(member));

        given(jwtUtil.generateToken(member, TokenType.PASSWORD_CHANGE_TOKEN))
                .willReturn(comppiToken);

        // when
        ComppiToken resultToken = forgetService.verifyPasswordCode(request);

        // then
        assertThat(resultToken).isNotNull();
        assertThat(resultToken.getTokenType()).isEqualTo(TokenType.PASSWORD_CHANGE_TOKEN);
        assertThat(resultToken.getToken()).isEqualTo(comppiToken.getToken());
    }

    @Test
    void verifyPasswordCode_틀린_인증코드로_요청하면_예외를_던집니다() {
        // given
        VerifyFindPasswordCodeRequest request = VerifyFindPasswordCodeRequest.builder()
                .email("kihong@naver.com")
                .phoneNumber("01012345678")
                .verificationCode("123456")
                .build();

        given(redisPort.verifyFindPasswordValidationCode(
                request.phoneNumber(),
                request.email(),
                request.verificationCode()
        )).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> forgetService.verifyPasswordCode(request))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void changePassword_올바른_토큰으로_요청시_패스워드를_변경합니다(){
        // given
        ComppiToken comppiToken = ComppiToken.of(TokenType.PASSWORD_CHANGE_TOKEN, "token");

        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .changePasswordToken(comppiToken.getToken())
                .newPassword("password")
                .build();

        Member member = Member.builder()
                .id(1L)
                .email("kihong@naver.com")
                .phoneNumber("01012345678")
                .build();

        Passport passport = new Passport(1L, Role.MEMBER);

        given(jwtUtil.validateToken(comppiToken.getToken(), comppiToken.getTokenType()))
                .willReturn(Optional.of(passport));

        given(memberQuery.findById(passport.memberId())).willReturn(Optional.of(member));

        given(redisPort.verifyChangePasswordToken(
                member.getPhoneNumber(),
                member.getEmail(),
                request.changePasswordToken()
        )).willReturn(true);

        forgetService.changePassword(request);

        // when
        then(memberCommand).should().update(any());
    }




}