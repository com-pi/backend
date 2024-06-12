package com.example.authserver.adapter.out.command;

import com.example.authserver.adapter.out.query.MemberDocument;
import com.example.authserver.adapter.out.query.MemberMongoRepository;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.domain.Member;
import com.example.common.exception.InternalServerException;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberCommandAdapter implements MemberCommand {

    private final MemberJpaRepository repository;
    private final MemberMongoRepository readRepository;

    @Override
    public void save(Member member) {
        MemberEntity memberEntity = MemberEntity.fromDomain(member);
        repository.save(memberEntity);
    }

    @Override
    @Transactional
    public void update(Member member) {
        MemberEntity memberEntity = repository.findById(member.getId())
                .orElseThrow(() -> new InternalServerException("멤버를 찾을 수 없습니다.", null));

        memberEntity.update(member);
    }

    @Override
    public void saveRead(Member member) {
        readRepository.save(MemberDocument.fromDomain(member));
    }

    @Override
    public void updateRead(Member member) {
        MemberDocument memberDocument = readRepository.findById(member.getId().toString())
                .orElseThrow(() -> new NotFoundException(MemberDocument.class));

        memberDocument.update(member);
    }


}
