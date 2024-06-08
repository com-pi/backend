package com.example.myplant.adapter.in.web.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCharacterCommand {
    private String characterNo;
    private String characterName;

    @Builder
    public CreateCharacterCommand(String characterNo, String characterName){
        this.characterNo = characterNo;
        this.characterName = characterName;
    }
}
