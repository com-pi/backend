//package com.example.myplant.adapter.in.web.controller;
//
//import com.example.common.annotation.Authenticate;
//import com.example.common.baseentity.CommonResponse;
//import com.example.common.domain.Role;
//import com.example.myplant.adapter.in.web.command.CharacterCommand;
//import com.example.myplant.application.service.CharacterService;
//import com.example.myplant.domain.Character;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/characters")
//@Tag(name = "캐릭터 관리", description = "캐릭터 추가 및 수정 API")
//public class CharacterController {
//
//    private final CharacterService characterService;
//
//    @Autowired
//    public CharacterController(CharacterService characterService) {
//        this.characterService = characterService;
//    }
//
//    @Authenticate(Role.ADMIN)
//    @PostMapping("/create")
//    @Operation(summary = "캐릭터 추가", description = "새로운 캐릭터를 추가합니다.")
//    public ResponseEntity<CommonResponse<Long>> createCharacter(@RequestBody CharacterCommand command) {
//        Long characterId = characterService.createCharacter(command);
//        return ResponseEntity.ok(new CommonResponse<>("Character added successfully", characterId));
//    }
//
//    @Authenticate(Role.ADMIN)
//    @PutMapping("/update/{id}")
//    @Operation(summary = "캐릭터 수정", description = "기존 캐릭터를 수정합니다.")
//    public ResponseEntity<CommonResponse<Long>> updateCharacter(@PathVariable Long id, @RequestBody CharacterCommand command) {
//        characterService.updateCharacter(id, command);
//        return ResponseEntity.ok(new CommonResponse<>("Character updated successfully", id));
//    }
//
//    @Authenticate(Role.ADMIN)
//    @DeleteMapping("/delete/{id}")
//    @Operation(summary = "캐릭터 삭제", description = "기존 캐릭터를 삭제합니다.")
//    public ResponseEntity<CommonResponse<Void>> deleteCharacter(@PathVariable Long id) {
//        characterService.deleteCharacter(id);
//        return ResponseEntity.ok(new CommonResponse<>("Character deleted successfully", null));
//    }
//
//    @Authenticate(Role.MEMBER)
//    @GetMapping("/{id}")
//    @Operation(summary = "캐릭터 ID로 캐릭터 조회", description = "ID를 통해 캐릭터를 조회합니다.")
//    public ResponseEntity<CommonResponse<Character>> getCharacterById(@PathVariable Long id) {
//        Character character = characterService.getCharacterById(id);
//        return ResponseEntity.ok(new CommonResponse<>("Character retrieved successfully", character));
//    }
//
//    @Authenticate(Role.MEMBER)
//    @GetMapping("/all")
//    @Operation(summary = "모든 캐릭터 조회", description = "모든 캐릭터를 조회합니다.")
//    public ResponseEntity<CommonResponse<List<Character>>> getAllCharacters() {
//        List<Character> characters = characterService.getAllCharacters();
//        return ResponseEntity.ok(new CommonResponse<>("Characters retrieved successfully", characters));
//    }
//}