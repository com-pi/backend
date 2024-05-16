package com.example.authserver.application.port.in;

import com.example.authserver.adapter.in.ModifyLocationRequest;
import com.example.common.domain.AddressValue;

public interface ModifyUseCase {

    void modifyMemberInfo(String nickName, String introduction);
    void modifyPassword(String oldPassword, String newPassword);
    AddressValue modifyLocation(ModifyLocationRequest request);

}
