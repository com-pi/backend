package com.example.authserver.domain;

public record SlackMessage (
        String text
) {
    public static SlackMessage joinMessage(String code, String phoneNumber) {
        return new SlackMessage(String.format("회원가입을 위한 인증번호는 %s 입니다. 인증번호를 사용해 회원가입을 완료해주세요." +
                " \n 발송 전화번호: %s", code, phoneNumber));
    }

    public static SlackMessage forgetIdMessage(String code, String phoneNumber) {
        return new SlackMessage(String.format("아이디 변경을 위한 인증번호는 %s 입니다. 인증번호를 입력해주세요." +
                " \n 발송 전화번호: %s", code, phoneNumber));
}

    public static SlackMessage findPasswordMessage(String s, String verificationCode) {
        return new SlackMessage(String.format("비밀번호 변경을 위한 인증번호는 %s 입니다. 인증번호를 입력해주세요." +
                " \n 발송 전화번호: %s", verificationCode, s));
    }
}
