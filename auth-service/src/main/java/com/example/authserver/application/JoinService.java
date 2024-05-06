package com.example.authserver.application;

import com.example.authserver.adapter.in.JoinRequest;
import com.example.authserver.application.port.in.JoinUseCase;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService implements JoinUseCase {

    private final MemberPort memberPort;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void join(JoinRequest joinRequest) {

        Member member = Member.newMemberFromRequest(joinRequest, passwordEncoder);

        memberPort.save(member);
    }

}
