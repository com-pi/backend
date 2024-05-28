package com.example.authserver.adapter.out;

import com.example.common.domain.Address;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.ArrayList;
import java.util.Optional;

public record AddressConvertResponse(
        @JsonAlias("documents")
        ArrayList<AddressConvertResult> addressResult
) {
    private static String 행정동_코드 = "H";

    public record AddressConvertResult(
            String regionType,
            String addressName,
            @JsonAlias("region_1depth_name")
            String sido,
            @JsonAlias("region_2depth_name")
            String sigungu,
            @JsonAlias("region_3depth_name")
            String eupmyundong,
            double x,
            double y
    ){}

    public Optional<Address> getAddress() {
        AddressConvertResult hResult =
                addressResult.stream().filter(a -> a.regionType.equals(행정동_코드)).findFirst()
                                .orElse(null);

        if (hResult == null) {
            return Optional.empty();
        }

        return Optional.of(Address.of(
           hResult.sido,
           hResult.sigungu,
           hResult.eupmyundong
        ));
    }
}
