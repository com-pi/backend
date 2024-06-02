package com.example.authserver.application.port.out.external;

import com.example.common.domain.Address;
import com.example.common.domain.Location;

import java.util.Optional;

public interface AddressConverterPort {

    Optional<Address> convertToAddress(Location location);

}
