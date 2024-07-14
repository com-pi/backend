package com.example.authserver.adapter.out.command;

import com.example.authserver.adapter.out.entity.MemberEntity;
import com.example.authserver.adapter.out.repository.MemberJpaRepository;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.domain.Member;
import com.example.common.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberCommandImpl implements MemberCommand {

    private final MemberJpaRepository jpaRepository;

    @Override
    @Transactional
    public void save(Member member) {
        MemberEntity memberEntity = MemberEntity.fromDomain(member);
        jpaRepository.save(memberEntity);
    }

    @Override
    @Transactional
    public void update(Member member) {
        MemberEntity memberEntity = jpaRepository.findById(member.getId())
                .orElseThrow(() -> new InternalServerException("멤버를 찾을 수 없습니다.", null));
        memberEntity.update(member);
    }

}
