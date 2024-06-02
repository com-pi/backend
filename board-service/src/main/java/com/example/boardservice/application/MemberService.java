package com.example.boardservice.application;

import com.example.boardservice.application.port.out.MemberPort;
import com.example.boardservice.domain.Member;
import com.example.common.domain.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberPort memberPort;

    public void integrateMember(Passport passport) {
        Optional<Member> storedMember = memberPort.findById(passport.memberId());

        if (storedMember.isEmpty()) {
            Member member = Member.fromPassport(passport);
            memberPort.saveAndFlush(member);

        } else {
            storedMember.get().update(passport);
        }
    }

}
