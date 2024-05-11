package com.example.authserver.application.port.out.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "send-sms", url = "https://naver.com")
public interface SMSPort {

    @PostMapping("/")
    void sendVerificationSMS();

}
