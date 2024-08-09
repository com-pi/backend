package com.example.authserver.adapter.in.response;

import com.example.authserver.domain.MemberBrief;

import java.util.List;

public record MemberBriefInfoResponse (
        List<MemberBrief> memberBriefList
) {
    public static MemberBriefInfoResponse of(List<MemberBrief> memberBriefList) {
        return new MemberBriefInfoResponse(memberBriefList);
    }
}
