package com.example.boardservice.application;

import com.example.boardservice.adapter.out.persistence.entity.MemberEntity;
import com.example.boardservice.application.port.out.MemberCommandPort;
import com.example.boardservice.application.port.out.MemberQueryPort;
import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.MemberBriefInfo;
import com.example.boardservice.domain.MemberBriefInfoList;
import com.example.common.domain.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
            memberCommandPort.save(member);
        }
    }

    public List<Member> getMemberList(List<Long> memberIdList) {
        MemberBriefInfoList memberBriefInfo =  memberQueryPort.getMemberList(memberIdList);
        List<MemberBriefInfo> memberBriefInfoList = memberBriefInfo.getMemberBriefList();
        return memberBriefInfoList.stream()
            .map(MemberBriefInfo::toMember)
            .toList();
    }

}
