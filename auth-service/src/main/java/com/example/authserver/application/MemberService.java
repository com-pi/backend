package com.example.authserver.application;

import com.example.authserver.adapter.in.response.MemberInfoResponse;
import com.example.authserver.adapter.in.response.MyInfoResponse;
import com.example.authserver.adapter.out.MemberEntity;
import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.in.MemberUseCase;
import com.example.authserver.application.port.out.external.AddressConverterPort;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.Member;
import com.example.authserver.exception.BadRequestException;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import com.example.common.exception.NotFoundException;
import com.example.imagemodule.application.port.ImageCommand;
import com.example.imagemodule.domain.ImageAndThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements MemberUseCase {

    private final MemberQuery memberQuery;
    private final MemberCommand memberCommand;
    private final ImageCommand imageCommand;
    private final AddressConverterPort addressConverterPort;


    @Override
    public MyInfoResponse getMyInfo() {
        Member me = memberQuery.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(Member.class));

        return MyInfoResponse.of(me);
    }

    @Override
    @Transactional
    public void modifyMyInfo(String nickName, String introduction) {
        Member member = memberQuery.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(Member.class));

        Member modifiedMember = member.updateInfo(nickName, introduction);

        memberCommand.update(modifiedMember);
    }

    @Override
    @Transactional
    public ImageAndThumbnail postProfileImage(MultipartFile profileImage) {
        Member member = memberQuery.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        ImageAndThumbnail imageAndThumbnail = imageCommand.saveProfileImage(profileImage);

        Member imageUpdatedMe = member.updateProfileImage(imageAndThumbnail);
        memberCommand.update(imageUpdatedMe);

        return imageAndThumbnail;
    }

    @Override
    @Transactional
    public Address modifyLocation(Location location) {
        Member member = memberQuery.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        Address address = addressConverterPort.convertToAddress(location)
                .orElseThrow(() -> new BadRequestException("주소 변환에 실패 했습니다."));

        Member locationUpdatedMember = member.updateLocation(location, address);

        memberCommand.update(locationUpdatedMember);
        return address;
    }


    @Override
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member foundMember = memberQuery.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        return MemberInfoResponse.from(foundMember);
    }

}
