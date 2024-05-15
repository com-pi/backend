package com.example.authserver.adapter.out;

import com.example.authserver.application.port.out.external.EmailPort;
import org.springframework.stereotype.Component;

@Component
public class EmailPortAdapter implements EmailPort {

    @Override
    public void sendPasswordEmail(String to, String password) {

        
    }

}
