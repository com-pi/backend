package com.example.authserver.application;

import com.example.authserver.adapter.in.request.ModifyMemberInfoRequest;
import com.example.authserver.adapter.in.response.MemberBriefInfoResponse;
import com.example.authserver.adapter.in.response.MemberInfoResponse;
import com.example.authserver.adapter.in.response.MyInfoResponse;
import com.example.authserver.adapter.out.entity.MemberEntity;
import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.in.MemberUseCase;
import com.example.authserver.application.port.out.external.AddressConverterPort;
import com.example.authserver.application.port.out.persistence.FollowQuery;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.application.port.out.persistence.MemberQuery;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.MemberBrief;
import com.example.authserver.exception.BadRequestException;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import com.example.common.exception.NotFoundException;
import com.example.imagemodule.application.port.ImageCommand;
import com.example.imagemodule.domain.ImageAndThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {

    private final MemberQuery memberQuery;
    private final FollowQuery followQuery;
    private final MemberCommand memberCommand;
    private final ImageCommand imageCommand;
    private final AddressConverterPort addressConverter;

    @Override
    public MyInfoResponse getMyInfo() {
        Member me = memberQuery.findById(PassportHolder.getPassport().memberId())
                .orElseThrow(() -> new NotFoundException(Member.class));

        Integer followerCount = followQuery.countFollower(me.getId());
        Integer followeeCount = followQuery.countFollowee(me.getId());

        return MyInfoResponse.of(me, followerCount, followeeCount);
    }

    @Override
    public MemberBriefInfoResponse getMemberBriefInfo(List<Long> memberIds) {
        List<MemberBrief> memberBriefList = new ArrayList<>();
        List<Long> notCached = new ArrayList<>();

        for(Long memberId : memberIds){
            MemberBrief briefById = memberQuery.findBriefById(memberId);
            if(briefById == null){
                notCached.add(memberId);
            } else {
                memberBriefList.add(briefById);
            }
        }

        memberBriefList.addAll(memberQuery.findAllBriefById(notCached));
        return MemberBriefInfoResponse.of(memberBriefList);
    }

    @Override
    public void modifyMemberInfo(ModifyMemberInfoRequest request) {
        ImageAndThumbnail imageAndThumbnail = new ImageAndThumbnail(null, null);

        if(request.getIsPicUploaded() && request.getProfileImage() != null){
            imageAndThumbnail = imageCommand.saveProfileImage(request.getProfileImage());
        }

        Member member = memberQuery.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        Location location = request.getLocation().toDomain();
        Address address = member.getAddress();

        if(location.isNull()){
            address = Address.empty();
        } else if(!location.equals(member.getLocation())) {
            address = addressConverter.convertToAddress(request.getLocation().toDomain())
                    .orElseThrow(() -> new BadRequestException("주소 변환에 실패 했습니다."));
        }

        ModifyMemberInfoCommand command = ModifyMemberInfoCommand.builder()
                .memberId(request.getMemberId())
                .nickName(request.getNickname())
                .introduction(request.getIntroduction())
                .isPicUploaded(request.getIsPicUploaded())
                .profileImageUrl(imageAndThumbnail.imageUrl())
                .thumbnailUrl(imageAndThumbnail.thumbnailUrl())
                .location(location)
                .address(address)
                .build();

        Member modifiedMember = member.updateInfo(command);

        memberCommand.update(modifiedMember);
    }


    @Override
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member foundMember = memberQuery.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MemberEntity.class));

        Integer followerCount = followQuery.countFollower(memberId);
        Integer followingCount = followQuery.countFollowee(memberId);
        Boolean isFollowed = followQuery.isFollowedByMember(PassportHolder.getPassport().memberId(), memberId);

        return MemberInfoResponse.from(foundMember, followerCount, followingCount, isFollowed);
    }

}
