package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.MemberEntity;
import com.example.boardservice.application.port.out.MemberQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberQueryAdapter implements MemberQueryPort {

    private final MemberRepository memberRepository;

    @Override
    public Optional<MemberEntity> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
