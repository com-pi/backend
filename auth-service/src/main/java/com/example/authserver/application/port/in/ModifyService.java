package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.ModifyLocationRequest;
import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.domain.Member;
import com.example.common.domain.AddressValue;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModifyService implements ModifyUseCase {

    private final MemberPort memberPort;

    @Override
    @Transactional
    public void modifyMemberInfo(String nickName, String introduction) {
        Member member = memberPort.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(Member.class));

        member.updateInfo(nickName, introduction);
    }

    // Todo

    @Override
    public void modifyPassword(String oldPassword, String newPassword) {

    }

    @Override
    public AddressValue modifyLocation(ModifyLocationRequest request) {
        return null;
    }
}
