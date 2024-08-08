package com.example.authserver.domain;

public record SlackMessage (
        String text
) {
    public static SlackMessage of(String code, String phoneNumber) {
        return new SlackMessage(String.format("회원가입을 위한 인증번호는 %s 입니다. 인증번호를 사용해 회원가입을 완료해주세요." +
                " \n 발송 전화번호: %s", code, phoneNumber));
    }
}
