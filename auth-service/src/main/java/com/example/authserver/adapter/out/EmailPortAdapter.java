package com.example.authserver.adapter.out;

import com.example.authserver.application.port.out.external.EmailPort;
import com.example.common.exception.InternalServerException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailPortAdapter implements EmailPort {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendPasswordEmail(String to, String password) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("꼼삐 임시 비밀번호 발급");
            mimeMessageHelper.setText(String.format("임시 비밀번호 : %s", password), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new InternalServerException("메일 전송 실패", e);
        }

    }


}
