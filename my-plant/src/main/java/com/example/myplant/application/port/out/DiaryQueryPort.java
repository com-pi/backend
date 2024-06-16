package com.example.myplant.application.port.out;

import com.example.myplant.domain.Diary;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface DiaryQueryPort {
    Diary getDiaryByDiaryId(Long diaryId);

    List<Diary> getDiaryStatus(LocalDate startDate, LocalDate endDate, Long memberId);

    List<Diary> getDiaryByMemberId(Long memberId, Pageable pageable);

    Diary getPersonalDiaryByDiaryId(Long diaryId, Long memberId);

    Diary getPublicDiaryByDiaryId(Long diaryId);
}
