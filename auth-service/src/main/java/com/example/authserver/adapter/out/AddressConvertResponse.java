package com.example.authserver.adapter.out;

import com.example.common.domain.Address;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.ArrayList;
import java.util.Optional;

public record AddressConvertResponse(
        @JsonAlias("documents")
        ArrayList<AddressConvertResult> addressResult
) {
    private static final String 행정동_코드 = "H";

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
        if (addressResult.isEmpty()) {
            return Optional.empty();
        }

        AddressConvertResult hResult =
                addressResult.stream()
                        .filter(a -> 행정동_코드.equals(a.regionType))
                        .findFirst()
                        .orElse(addressResult.get(0));

        return Optional.of(Address.of(
           hResult.sido,
           hResult.sigungu,
           hResult.eupmyundong
        ));
    }
}
