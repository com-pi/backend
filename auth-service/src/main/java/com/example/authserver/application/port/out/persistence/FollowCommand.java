package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.DeleteFollow;
import com.example.authserver.domain.Follow;

public interface FollowCommand {

    void save(Follow follow);

    void delete(DeleteFollow deleteFollow);

}
