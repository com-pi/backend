package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codeType;

    @Column(nullable = false)
    private String codeValue;

    @Column(nullable = false)
    private String codeName;

    public CommonCode(String codeType, String codeValue, String codeName){
        this.codeType = codeType;
        this.codeValue = codeValue;
        this.codeName = codeName;
    }
}
