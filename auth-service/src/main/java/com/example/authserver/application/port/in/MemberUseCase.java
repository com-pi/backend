package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.request.ModifyLocationRequest;
import com.example.authserver.adapter.in.response.MemberInfoResponse;
import com.example.authserver.adapter.in.response.MyInfoResponse;
import com.example.common.domain.AddressValue;
import com.example.imagemodule.domain.ImageAndThumbnail;
import org.springframework.web.multipart.MultipartFile;

public interface MemberUseCase {

    MyInfoResponse getMyInfo();
    MemberInfoResponse getMemberInfo(Long memberId);

    void modifyMemberInfo(String nickName, String introduction);
    ImageAndThumbnail postProfileImage(MultipartFile profileImage);
    AddressValue modifyLocation(ModifyLocationRequest request);


}
