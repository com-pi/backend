package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.Member;

public interface MemberCommand {

    Member save(Member member);
    void update(Member member);

}
