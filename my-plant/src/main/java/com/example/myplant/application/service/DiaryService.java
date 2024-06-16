package com.example.myplant.application.service;

import com.example.common.exception.UnauthorizedException;
import com.example.imagemodule.application.port.ImageCommand;
import com.example.imagemodule.application.port.SaveImagesCommand;
import com.example.imagemodule.domain.MinioBucket;
import com.example.imagemodule.util.ObjectUrlMapper;
import com.example.myplant.adapter.in.web.command.GetDiaryStatusCommand;
import com.example.myplant.application.port.in.DiaryUseCase;
import com.example.myplant.application.port.out.DiaryCommandPort;
import com.example.myplant.application.port.out.DiaryQueryPort;
import com.example.myplant.domain.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService implements DiaryUseCase {

    private final DiaryCommandPort diaryCommandPort;
    private final DiaryQueryPort diaryQueryPort;
    private final ImageCommand imageCommand;
    private final ObjectUrlMapper objectUrlMapper;

    @Override
    @Transactional
    public Long registerDiary(Diary diary, List<MultipartFile> imageFiles) {
        diary.updateImageUrlList(getImageUrls(imageFiles));
        return diaryCommandPort.save(diary);
    }

    @Override
    @Transactional
    public Long updateDiary(Diary diary, List<MultipartFile> imageFiles) {
        Diary originDiary = diaryQueryPort.getDiaryByDiaryId(diary.getDiaryId());
        validatePermission(originDiary.getMemberId(), diary.getMemberId());

        List<String> imageUrlList = CollectionUtils.isEmpty(imageFiles)
                ? originDiary.getImageUrlList()
                : getImageUrls(imageFiles);
        diary.updateImageUrlList(imageUrlList);

        diaryCommandPort.update(diary);
        return diary.getDiaryId();
    }

    @Override
    @Transactional
    public Long deleteDiary(Diary diary) {
        Diary originDiary = diaryQueryPort.getDiaryByDiaryId(diary.getDiaryId());
        validatePermission(originDiary.getMemberId(), diary.getMemberId());

        diaryCommandPort.delete(diary);
        return diary.getDiaryId();
    }

    @Override
    public List<Diary> getDiaryStatus(GetDiaryStatusCommand command) {
        return diaryQueryPort.getDiaryStatus(command.getStartDate(), command.getEndDate(), command.getMemberId());
    }

    @Override
    public List<Diary> getDiaryByMemberId(Long memberId, Pageable pageable) {
        return diaryQueryPort.getDiaryByMemberId(memberId, pageable);
    }

    @Override
    public Diary getDiaryByDiaryId(Long diaryId) {
        return diaryQueryPort.getPublicDiaryByDiaryId(diaryId);
    }

    @Override
    public Diary getPersonalDiaryByDiaryId(Diary diary) {
        return diaryQueryPort.getPersonalDiaryByDiaryId(diary.getDiaryId(), diary.getMemberId());
    }


    /**
     * private
     */
    private List<String> getImageUrls(List<MultipartFile> imageFiles) {
        List<String> objectNames = imageCommand.saveImages(
                new SaveImagesCommand(imageFiles, MinioBucket.DIARY_IMAGES)
        );
        return objectUrlMapper.toUrl(objectNames, MinioBucket.DIARY_IMAGES);
    }

    private void validatePermission(final Long originMemberId, final Long memberId) {
        if(!Objects.equals(originMemberId, memberId)) {
            throw new UnauthorizedException("일지를 수정하거나 삭제할 권한이 없습니다.");
        }
    }
}