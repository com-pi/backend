package com.example.encycloservice.adapter.out.persistence.entity;

import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.encycloservice.domain.MyEncyclopedia;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "my_encyclopedia")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
public class MyEncyclopediaEntity extends DeletedAtAbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String title;
    private String coverImageUrl;

    @OneToMany(mappedBy = "myEncyclopediaEntity")
    private List<EncyclopediaPlantEntity> plantCollection;

    public static MyEncyclopediaEntity fromDomain(MyEncyclopedia myEncyclopedia) {
        return MyEncyclopediaEntity.builder()
                .id(myEncyclopedia.getId())
                .memberId(myEncyclopedia.getMemberId())
                .title(myEncyclopedia.getTitle())
                .coverImageUrl(myEncyclopedia.getCoverImageUrl())
                .build();
    }

    public MyEncyclopedia toDomain() {
        return MyEncyclopedia.builder()
                .id(id)
                .memberId(memberId)
                .title(title)
                .coverImageUrl(coverImageUrl)
                .plantCollection(
                        plantCollection.stream()
                                .map(EncyclopediaPlantEntity::toBrief)
                                .toList())
                .build();
    }

}