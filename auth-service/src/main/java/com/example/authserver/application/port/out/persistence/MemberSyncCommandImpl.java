package com.example.authserver.application.port.out.persistence;

import com.example.authserver.adapter.out.entity.MemberDocument;
import com.example.authserver.adapter.out.repository.MemberMongoRepository;
import com.example.authserver.domain.Member;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberSyncCommandImpl implements MemberSyncCommand {

    private final MemberMongoRepository mongoRepository;
    
    @Override
    public void save(Member member) {
        mongoRepository.save(MemberDocument.fromDomain(member));
    }

    @Override
    public void update(Member member) {
        MemberDocument memberDocument = mongoRepository.findById(member.getId().toString())
                .orElseThrow(() -> new NotFoundException(MemberDocument.class));

        memberDocument.update(member);
    }
}
