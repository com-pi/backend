package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String imageUrl;

    private boolean isPublic;

    @OneToOne(mappedBy = "diary")
    private PlantDiary plantDiary;

    @Builder
    public Diary(String title, String content, String imageUrl, boolean isPublic){
        this.title = title;
        this.content= content;
        this.imageUrl = imageUrl;
        this.isPublic = isPublic;
    }
}