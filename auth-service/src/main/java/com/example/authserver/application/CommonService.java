package com.example.authserver.application;

import com.example.authserver.application.port.in.CommonUseCase;
import com.example.authserver.application.port.out.external.AddressConverterPort;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService implements CommonUseCase {

    private final AddressConverterPort addressConverterPort;

    @Override
    public Address getAddress(Location location) {
        return addressConverterPort.convertToAddress(location)
                .orElseThrow(() -> new NotFoundException("주소 정보를 조회할 수 없습니다. 다른 좌표로 다시 시도해주세요"));
    }

}
