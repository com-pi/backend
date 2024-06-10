package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Hashtag;

import java.util.List;

public interface HashtagCommandPort {

    void save(List<Hashtag> hashtagList);

}
