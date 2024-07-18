package com.example.authserver.application.port.in;

import com.example.common.domain.Address;
import com.example.common.domain.Location;

public interface CommonUseCase {

    Address getAddress(Location location);

}
