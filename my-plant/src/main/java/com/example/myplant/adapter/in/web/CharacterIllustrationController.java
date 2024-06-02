package com.example.myplant.adapter.in.web;

import com.example.common.baseentity.CommonResponse;
import com.example.myplant.application.service.CharacterIllustrationService;
import com.example.myplant.domain.CharacterIllustration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/character-illustrations")
@Tag(name = "캐릭터 일러스트 관리", description = "캐릭터 일러스트 추가 및 수정 API")
public class CharacterIllustrationController {
    private final CharacterIllustrationService characterIllustrationService;

    @Autowired
    public CharacterIllustrationController(CharacterIllustrationService characterIllustrationService) {
        this.characterIllustrationService = characterIllustrationService;
    }

    @PostMapping("/add")
    @Operation(summary = "캐릭터 일러스트 추가", description = "새로운 캐릭터 일러스트를 추가합니다.")
    public ResponseEntity<CommonResponse<CharacterIllustration>> addCharacterIllustration(@RequestBody CharacterIllustration illustration) {
        CharacterIllustration addedIllustration = characterIllustrationService.createCharacterIllustration(illustration);
        return ResponseEntity.ok(new CommonResponse<>("Character illustration added successfully",addedIllustration));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "캐릭터 일러스트 수정", description = "기존 캐릭터 일러스트를 수정합니다.")
    public ResponseEntity<CommonResponse<CharacterIllustration>> updateCharacterIllustration(@PathVariable Long id, @RequestBody CharacterIllustration illustrationDetails) {
        CharacterIllustration updatedIllustration = characterIllustrationService.updateCharacterIllustration(id, illustrationDetails);
        return ResponseEntity.ok(new CommonResponse<>("Character illustration updated successfully",updatedIllustration));
    }

    @GetMapping("/list")
    @Operation(summary = "캐릭터 일러스트 목록 조회", description = "모든 캐릭터 일러스트를 조회합니다.")
    public ResponseEntity<CommonResponse<List<CharacterIllustration>>> getAllCharacterIllustrations() {
        List<CharacterIllustration> illustrations = characterIllustrationService.getAllCharacterIllustrations();
        return ResponseEntity.ok(new CommonResponse<>("Character illustrations retrieved successfully", illustrations));
    }
}