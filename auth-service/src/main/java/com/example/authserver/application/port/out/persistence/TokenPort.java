package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.ComPToken;

public interface TokenPort {

    void saveToken(ComPToken token);
    boolean validateToken(ComPToken token);

}
