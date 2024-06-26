package com.example.authserver.application;

import com.example.common.domain.Location;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ModifyMemberRequest(
        Long memberId,
        MultipartFile profileImage,
        Location location,
        String nickName,
        String introduction
) {
}
