package com.example.authserver.adapter.in;

import com.example.authserver.aop.filter.PassportHolder;
import com.example.authserver.application.port.in.FollowUseCase;
import com.example.authserver.domain.FollowerPagingResult;
import com.example.authserver.domain.FollowingPagingResult;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Passport;
import com.example.common.domain.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
@Tag(name = "팔로우", description = "팔로우/팔로우 끊기, 팔로우/팔로잉 목록 조회")
public class FollowController {

    private final FollowUseCase followUseCase;

    @PostMapping("")
    @Authenticate(Role.MEMBER)
    @Operation(summary = "팔로우", description = "회원을 팔로우 합니다.")
    public ResponseEntity<CommonResponse<Void>> follow (
            @RequestParam Long followeeId
    ) {
        followUseCase.follow(followeeId);
        return CommonResponse.okWithMessage("팔로우 성공", null);
    }

    @DeleteMapping("")
    @Authenticate(Role.MEMBER)
    @Operation(summary = "팔로우 취소", description = "팔로우를 취소 합니다.")
    public ResponseEntity<CommonResponse<Void>> unfollow (
            @RequestParam Long followeeId
    ) {
        followUseCase.unFollow(followeeId);
        return CommonResponse.okWithMessage("팔로우 취소 성공", null);
    }

    @GetMapping("/{memberId}/follower")
    @Authenticate(Role.MEMBER)
    @Operation(summary = "팔로워 목록 조회", description = "회원을 팔로우하는 회원 목록을 조회합니다.")
    public ResponseEntity<CommonResponse<FollowerPagingResult>> getFollowerList (
            @PathVariable Long memberId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        Passport passport = PassportHolder.getPassport();
        FollowerPagingResult followerList = followUseCase.getFollowerList(passport.memberId(), memberId, page, size);
        return CommonResponse.okWithMessage("팔로워 목록 조회 성공", followerList);
    }

    @GetMapping("/{memberId}/follow")
    @Authenticate(Role.MEMBER)
    @Operation(summary = "팔로잉 목록 조회", description = "회원이 팔로우하는 회원 목록을 조회합니다.")
    public ResponseEntity<CommonResponse<FollowingPagingResult>> getFollowingList (
            @PathVariable Long memberId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        Passport passport = PassportHolder.getPassport();
        FollowingPagingResult followingList = followUseCase.getFollowingList(passport.memberId(), memberId, page, size);
        return CommonResponse.okWithMessage("팔로잉 목록 조회 성공", followingList);
    }

    @GetMapping("/follower")
    @Authenticate(Role.MEMBER)
    @Operation(summary = "나의 팔로워 목록 조회", description = "나를 팔로우 하는 회원 목록을 조회합니다.")
    public ResponseEntity<CommonResponse<FollowerPagingResult>> getMyFollowerList (
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        Passport passport = PassportHolder.getPassport();
        FollowerPagingResult followerList = followUseCase.getFollowerList(passport.memberId(), passport.memberId(), page, size);
        return CommonResponse.okWithMessage("팔로워 목록 조회 성공", followerList);
    }

    @GetMapping("/following")
    @Authenticate(Role.MEMBER)
    @Operation(summary = "나의 팔로잉 목록 조회", description = "내가 팔로잉 하는 회원 목록을 조회합니다.")
    public ResponseEntity<CommonResponse<FollowingPagingResult>> getMyFollowingList (
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        Passport passport = PassportHolder.getPassport();
        FollowingPagingResult followingList = followUseCase.getFollowingList(passport.memberId(), passport.memberId(), page, size);
        return CommonResponse.okWithMessage("팔로잉 목록 조회 성공", followingList);
    }

}
