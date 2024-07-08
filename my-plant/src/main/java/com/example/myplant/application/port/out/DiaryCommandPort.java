package com.example.myplant.application.port.out;

import com.example.myplant.domain.Diary;

public interface DiaryCommandPort {

    Long save(Diary diary);

    void update(Diary diary);

    void delete(Diary diary);

    void postDiaryArticle(Diary diary);

}
