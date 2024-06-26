package com.example.authserver.application;

import com.example.authserver.adapter.in.request.ModifyMemberInfoRequest;
import com.example.authserver.adapter.in.response.MemberInfoResponse;
import com.example.authserver.adapter.in.response.MyInfoResponse;
import com.example.authserver.adapter.out.command.MemberEntity;
import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.in.MemberUseCase;
import com.example.authserver.application.port.out.external.AddressConverterPort;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.Member;
import com.example.authserver.exception.BadRequestException;
import com.example.common.domain.Address;
import com.example.common.exception.NotFoundException;
import com.example.imagemodule.application.port.ImageCommand;
import com.example.imagemodule.domain.ImageAndThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {

    private final MemberQuery memberQuery;
    private final MemberCommand memberCommand;
    private final ImageCommand imageCommand;
    private final AddressConverterPort addressConverter;

    @Override
    public MyInfoResponse getMyInfo() {
        Member me = memberQuery.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(Member.class));

        return MyInfoResponse.of(me);
    }

    @Override
    public void modifyMemberInfoRequest(ModifyMemberInfoRequest request) {
        ImageAndThumbnail imageAndThumbnail = new ImageAndThumbnail(null, null);
        if(request.getProfileImage() != null){
            imageAndThumbnail = imageCommand.saveProfileImage(request.getProfileImage());
        }

        Member member = memberQuery.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        Address address = member.getAddress();
        if(!request.getLocation().toDomain().equals(member.getLocation())) {
            address = addressConverter.convertToAddress(request.getLocation().toDomain())
                    .orElseThrow(() -> new BadRequestException("주소 변환에 실패 했습니다."));
        }

        ModifyMemberInfoCommand command = ModifyMemberInfoCommand.builder()
                .memberId(request.getMemberId())
                .nickName(request.getNickname())
                .introduction(request.getIntroduction())
                .profileImageUrl(imageAndThumbnail.imageUrl())
                .thumbnailUrl(imageAndThumbnail.thumbnailUrl())
                .location(request.getLocation().toDomain())
                .address(address)
                .build();

        Member modifiedMember = member.updateInfo(command);

        memberCommand.update(modifiedMember);
    }


    @Override
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member foundMember = memberQuery.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        return MemberInfoResponse.from(foundMember);
    }



}
