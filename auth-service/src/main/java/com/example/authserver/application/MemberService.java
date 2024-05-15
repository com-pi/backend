package com.example.authserver.application;

import com.example.authserver.adapter.in.MemberInfoResponse;
import com.example.authserver.adapter.in.ModifyLocationRequest;
import com.example.authserver.adapter.in.MyInfoResponse;
import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.in.MemberUseCase;
import com.example.authserver.application.port.out.persistence.MemberPort;
import com.example.authserver.domain.Member;
import com.example.common.domain.AddressValue;
import com.example.common.exception.NotFoundException;
import com.example.imagemodule.application.port.ImageCommandPort;
import com.example.imagemodule.domain.ImageAndThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements MemberUseCase {

    private final MemberPort memberPort;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ImageCommandPort imageCommandPort;

    @Override
    public MyInfoResponse getMyInfo() {
        Member me = memberPort.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(Member.class));

        return MyInfoResponse.of(me);
    }

    @Override
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member foundMember = memberPort.findById(memberId)
                .orElseThrow(() -> new NotFoundException(Member.class));

        return MemberInfoResponse.from(foundMember);
    }

    @Override
    @Transactional
    public void modifyMemberInfo(String nickName, String introduction) {
        Member member = memberPort.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(Member.class));

        member.updateInfo(nickName, introduction);
    }

    @Override
    @Transactional
    public ImageAndThumbnail postProfileImage(MultipartFile profileImage) {
        Member me = memberPort.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(Member.class));

        ImageAndThumbnail imageAndThumbnail = imageCommandPort.saveProfileImage(profileImage);

        me.updateProfileImage(imageAndThumbnail);

        return imageAndThumbnail;
    }

    @Override
    public void modifyPassword(String oldPassword, String newPassword) {
        // Todo
    }

    @Override
    public AddressValue modifyLocation(ModifyLocationRequest request) {
        return null;
    }
}
