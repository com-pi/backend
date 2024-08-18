package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.MemberEntity;
import com.example.boardservice.application.port.out.MemberQueryPort;
import com.example.boardservice.domain.MemberBriefInfoList;
import com.example.boardservice.security.PassportHolder;
import com.example.common.baseentity.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberQueryAdapter implements MemberQueryPort {

    private final MemberRepository memberRepository;
    private final MemberClient memberClient;

    @Override
    public Optional<MemberEntity> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public MemberBriefInfoList getMemberList(List<Long> memberIdList) {
        String passportJson = PassportHolder.getPassportJson();
        ResponseEntity<CommonResponse<MemberBriefInfoList>> responseList = memberClient.getMemberList(passportJson, memberIdList);
        return Objects.requireNonNull(responseList.getBody()).data();
    }
}
