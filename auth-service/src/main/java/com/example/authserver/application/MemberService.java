package com.example.authserver.application;

import com.example.authserver.adapter.in.request.ModifyLocationRequest;
import com.example.authserver.adapter.in.response.MemberInfoResponse;
import com.example.authserver.adapter.in.response.MyInfoResponse;
import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.in.MemberUseCase;
import com.example.authserver.adapter.out.MemberJpaRepository;
import com.example.authserver.adapter.out.MemberEntity;
import com.example.common.domain.AddressValue;
import com.example.common.exception.NotFoundException;
import com.example.imagemodule.application.port.ImageCommandPort;
import com.example.imagemodule.domain.ImageAndThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements MemberUseCase {

    private final MemberJpaRepository memberJpaRepository;
    private final ImageCommandPort imageCommandPort;

    @Override
    public MyInfoResponse getMyInfo() {
        MemberEntity me = memberJpaRepository.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        return MyInfoResponse.of(me);
    }

    @Override
    public MemberInfoResponse getMemberInfo(Long memberId) {
        MemberEntity foundMemberEntity = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        return MemberInfoResponse.from(foundMemberEntity);
    }

    @Override
    @Transactional
    public void modifyMemberInfo(String nickName, String introduction) {
        MemberEntity memberEntity = memberJpaRepository.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        memberEntity.updateInfo(nickName, introduction);
    }

    @Override
    @Transactional
    public ImageAndThumbnail postProfileImage(MultipartFile profileImage) {
        MemberEntity me = memberJpaRepository.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        ImageAndThumbnail imageAndThumbnail = imageCommandPort.saveProfileImage(profileImage);

        me.updateProfileImage(imageAndThumbnail);

        return imageAndThumbnail;
    }

    @Override
    public AddressValue modifyLocation(ModifyLocationRequest request) {
        return null;
    }
}
