package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.request.ModifyMemberInfoRequest;
import com.example.authserver.adapter.in.response.MemberInfoResponse;
import com.example.authserver.adapter.in.response.MyInfoResponse;

public interface MemberUseCase {

    void modifyMemberInfoRequest(ModifyMemberInfoRequest request);

    MemberInfoResponse getMemberInfo(Long memberId);
    MyInfoResponse getMyInfo();

}
