package com.example.authserver.domain;

import com.example.common.domain.Location;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ModifyMemberRequest(
        Long memberId,
        Location location,
        String nickName,
        String introduction,
        Boolean isPicUploaded,
        MultipartFile profileImage
) {
}
