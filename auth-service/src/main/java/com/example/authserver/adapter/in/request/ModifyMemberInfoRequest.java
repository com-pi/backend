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
    private final @Size(max = 10) @NotBlank String nickname;

    @NotNull
    private final @Size(max = 3000) @NotBlank String introduction;

    @NotNull
    private final LocationRequest location;

    private final MultipartFile profileImage;

    @Builder
    public ModifyMemberInfoRequest(Long memberId, String nickname, String introduction, LocationRequest location, MultipartFile profileImage) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.introduction = introduction;
        this.location = location;
        this.profileImage = profileImage;
        super.validateSelf();
    }

    public ModifyMemberRequest toDomain(Long memberId, MultipartFile profileImage) {
        return ModifyMemberRequest.builder()
                .memberId(memberId)
                .location(location.toDomain())
                .introduction(introduction)
                .nickName(nickname)
                .profileImage(profileImage)
                .build();
    }

}
