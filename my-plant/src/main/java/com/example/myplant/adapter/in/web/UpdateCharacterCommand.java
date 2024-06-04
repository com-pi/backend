package com.example.myplant.adapter.in.web;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCharacterCommand {
    private Long id;
    private String characterNo;
    private String characterName;

    @Builder
    public UpdateCharacterCommand(Long id, String characterNo, String characterName){
        this.id = id;
        this.characterNo = characterNo;
        this.characterName = characterName;
    }
}
