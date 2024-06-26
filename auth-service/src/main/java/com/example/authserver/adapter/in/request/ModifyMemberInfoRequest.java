package com.example.authserver.adapter.in.request;

import com.example.authserver.application.ModifyMemberRequest;
import com.example.common.baseentity.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public final class ModifyMemberInfoRequest extends SelfValidating<ModifyMemberInfoRequest> {

    @NotNull
    private final Long memberId;

    @NotNull
    @NotBlank(message = "닉넴임은 공백일 수 없습니다.")
    @Size(max = 10, message = "닉네임은 10자를 넘을 수 없습니다.")
    private final String nickname;

    @Size(max = 1000, message = "소개글은 300 자를 넘길 수 없습니다.")
    private final String introduction;

    private final LocationRequest location;

    @NotNull
    private final Boolean isPicUploaded;

    private final MultipartFile profileImage;

    @Builder
    public ModifyMemberInfoRequest(Long memberId, String nickname, String introduction, LocationRequest location, Boolean isPicUploaded, MultipartFile profileImage) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.introduction = introduction;
        this.location = location;
        this.isPicUploaded = isPicUploaded;
        this.profileImage = profileImage;
        super.validateSelf();
    }

    public ModifyMemberRequest toDomain(Long memberId, MultipartFile profileImage) {
        return ModifyMemberRequest.builder()
                .memberId(memberId)
                .location(location.toDomain())
                .introduction(introduction)
                .nickName(nickname)
                .isPicUploaded(isPicUploaded)
                .profileImage(profileImage)
                .build();
    }

}
