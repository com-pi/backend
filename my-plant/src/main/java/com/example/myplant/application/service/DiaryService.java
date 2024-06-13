//package com.example.myplant.application.service;
//
//import com.example.myplant.adapter.in.web.command.DiaryCommand;
//import com.example.myplant.adapter.in.web.command.UpdateDiaryCommand;
//import com.example.myplant.application.port.in.DiaryUseCase;
//import com.example.myplant.application.port.out.DiaryPort;
//import com.example.imagemodule.application.port.ImageCommand;
//import com.example.imagemodule.application.port.SaveImagesCommand;
//import com.example.imagemodule.domain.MinioBucket;
//import com.example.myplant.domain.Diary;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class DiaryService implements DiaryUseCase {
//    private final DiaryPort diaryPort;
//    private final ImageCommand imageCommand;
//
//    @Autowired
//    public DiaryService(DiaryPort diaryPort, ImageCommand imageCommand) {
//        this.diaryPort = diaryPort;
//        this.imageCommand = imageCommand;
//    }
//
//    @Override
//    @Transactional
//    public Long createDiary(DiaryCommand command){
//        List<String> imageUrls = imageCommand.saveImages(new SaveImagesCommand(command.getImages(), MinioBucket.DIARY_IMAGES));
//
//        Diary diary = Diary.builder()
//                .plantId(command.getPlantId())
//                .title(command.getTitle())
//                .content(command.getContent())
//                .isPublic(command.isPublic())
//                .managementTypes(command.getManagementTypes())
//                .date(command.getDate())
//                .images(imageUrls)
//                .build();
//
//        return diaryPort.saveDiary(diary).getId();
//    }
//
//    @Override
//    public void updateDiary(UpdateDiaryCommand command) {
//        List<String> imageUrls = imageCommand.saveImages(new SaveImagesCommand(command.getImages(), MinioBucket.DIARY_IMAGES));
//
//        Diary diary = diaryPort.findById(command.getId())
//                .orElseThrow(() -> new RuntimeException("Diary not found"));
//
//        diary.setTitle(command.getTitle());
//        diary.setContent(command.getContent());
//        diary.setPublic(command.isPublic());
//        diary.setImages(imageUrls);
//
//        diaryPort.saveDiary(diary);
//    }
//
//    @Override
//    public void deleteDiary(Long id) {
//        diaryPort.deleteById(id);
//    }
//    @Override
//    public List<Diary> getAllDiaries() {
//        return diaryPort.loadAllDiaries();
//    }
//
//
//}