package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.response.MemberInfoResponse;
import com.example.authserver.adapter.in.response.MyInfoResponse;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import com.example.imagemodule.domain.ImageAndThumbnail;
import org.springframework.web.multipart.MultipartFile;

public interface MemberUseCase {

    MemberInfoResponse getMemberInfo(Long memberId);

    MyInfoResponse getMyInfo();
    void modifyMyInfo(String nickName, String introduction);
    ImageAndThumbnail postProfileImage(MultipartFile profileImage);
    Address modifyLocation(Location locationModify);

}
