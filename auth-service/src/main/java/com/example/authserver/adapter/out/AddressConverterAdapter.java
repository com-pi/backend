package com.example.authserver.adapter.out;

import com.example.authserver.application.port.out.external.AddressConverterPort;
import com.example.authserver.util.Secret;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressConverterAdapter implements AddressConverterPort {

    private final KakaoMapClient kakaoMapClient;

    @Override
    public Optional<Address> convertToAddress(Location location) {

        try {
            AddressConvertResponse response = kakaoMapClient.getAddress(
                    Secret.KAKAO_APP_KEY,
                    location.getLongitude(),
                    location.getLatitude()
            );

            return response.getAddress();
        } catch (Exception e){
            return Optional.empty();
        }

    }

}
