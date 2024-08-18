package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.domain.MemberBriefInfoList;
import com.example.common.baseentity.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "auth-service", url = "${feign.client.config.read-member.url}")
public interface MemberClient {
    @GetMapping("/member/brief")
    ResponseEntity<CommonResponse<MemberBriefInfoList>> getMemberList(
            @RequestHeader String passport,
            @RequestParam List<Long> memberId
    );

}
