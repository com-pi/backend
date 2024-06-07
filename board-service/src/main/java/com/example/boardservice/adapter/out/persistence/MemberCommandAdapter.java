package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.MemberEntity;
import com.example.boardservice.application.port.out.MemberCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCommandAdapter implements MemberCommandPort {

    private final MemberRepository memberRepository;


    @Override
    public void saveAndFlush(MemberEntity member) {
        memberRepository.saveAndFlush(member);
    }

    @Override
    public void save(MemberEntity member) {
        memberRepository.save(member);
    }
}
