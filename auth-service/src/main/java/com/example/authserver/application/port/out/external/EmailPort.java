package com.example.authserver.application.port.out.external;

public interface EmailPort {

    void sendPasswordEmail(String to, String password);

}
