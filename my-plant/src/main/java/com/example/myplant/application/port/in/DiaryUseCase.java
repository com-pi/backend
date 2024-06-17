package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.command.GetDiaryStatusCommand;
import com.example.myplant.domain.Diary;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DiaryUseCase {

    Long registerDiary(Diary diary, List<MultipartFile> imageFiles);

    Long updateDiary(Diary diary, List<MultipartFile> imageFiles);

    Long deleteDiary(Diary diary);

    List<Diary> getDiaryStatus(GetDiaryStatusCommand command);

    List<Diary> getDiaryByMemberId(Long memberId, Pageable pageable);

    Diary getDiaryById(Long diaryId);

    Diary getPersonalDiaryById(Diary diary);
}
