package com.example.myplant.adapter.in.web;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.myplant.application.service.CharacterService;
import com.example.myplant.domain.Character;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
@Tag(name = "캐릭터 관리", description = "캐릭터 추가 및 수정 API")
public class CharacterController {
    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping("/add")
    @Operation(summary = "캐릭터 추가", description = "새로운 캐릭터를 추가합니다.")
    @Authenticate(Role.ADMIN)
    public ResponseEntity<CommonResponse<Character>> addCharacter(@RequestBody Character character) {
        Character addedCharacter = characterService.createCharacter(character);
        return ResponseEntity.ok(new CommonResponse<>("Character added successfully",addedCharacter));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "캐릭터 수정", description = "기존 캐릭터를 수정합니다.")
    @Authenticate(Role.ADMIN)
    public ResponseEntity<CommonResponse<Character>> updateCharacter(@PathVariable Long id, @RequestBody Character characterDetails) {
        Character updatedCharacter = characterService.updateCharacter(id, characterDetails);
        return ResponseEntity.ok(new CommonResponse<>("Character updated successfully",updatedCharacter));
    }

    @GetMapping("/list")
    @Operation(summary = "캐릭터 목록 조회", description = "모든 캐릭터를 조회합니다.")
    public ResponseEntity<CommonResponse<List<Character>>> getAllCharacters() {
        List<Character> characters = characterService.getAllCharacters();
        return ResponseEntity.ok(new CommonResponse<>("Characters retrieved successfully",characters));
    }
}