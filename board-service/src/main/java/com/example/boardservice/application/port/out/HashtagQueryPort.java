package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Hashtag;

public interface HashtagQueryPort {

    Hashtag findHashtagByName(String name);

}
