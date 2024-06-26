package com.example.encycloservice.adapter.in.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MyEncyclopediaCreateRequest(
        @NotNull(message = "도감 이름을 입력해주세요.")
        @NotBlank(message = "도감 이름을 입력해주세요.")
        @Size(max = 12, message = "도감 이름은 12자 이내로 입력해주세요.")
        String title
) {

}