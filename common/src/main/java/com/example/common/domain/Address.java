package com.example.common.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
public class Address {

    private String sido;
    private String sigungu;
    private String eupmyundong;

    public static Address of(String sido, String sigungu, String eupmyundong) {
        return new Address(sido, sigungu, eupmyundong);
    }

    public static Address empty() {
        return new Address("", "", "");
    }

}
