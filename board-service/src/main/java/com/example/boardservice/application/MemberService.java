package com.example.boardservice.application;

import com.example.boardservice.adapter.out.persistence.entity.MemberEntity;
import com.example.boardservice.application.port.out.MemberCommandPort;
import com.example.boardservice.application.port.out.MemberQueryPort;
import com.example.common.domain.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberCommandPort memberCommandPort;
    private final MemberQueryPort memberQueryPort;

    @Transactional
    public void integrateMember(Passport passport) {
        Optional<MemberEntity> storedMember = memberQueryPort.findById(passport.memberId());

        if (storedMember.isEmpty()) {
            MemberEntity member = MemberEntity.fromPassport(passport);
            memberCommandPort.saveAndFlush(member);

        } else {
            MemberEntity member = storedMember.get();
            member.update(passport);
            memberCommandPort.save(member);
        }
    }

}
