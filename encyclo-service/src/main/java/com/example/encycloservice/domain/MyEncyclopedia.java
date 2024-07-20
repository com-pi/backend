package com.example.encycloservice.domain;

import com.example.common.domain.Passport;
import com.example.common.exception.CommonException;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString(exclude = "plantCollection")
@EqualsAndHashCode(of = {"memberId", "title"})
public class MyEncyclopedia {

    private Long id;
    private Long memberId;
    private String title;
    private String coverImageUrl;
    private List<PlantBrief> plantCollection = new ArrayList<>();

    public static MyEncyclopedia create(MyEncyclopediaCreate myEncyclopediaCreate) {
        return MyEncyclopedia.builder()
                .title(myEncyclopediaCreate.title())
                .memberId(myEncyclopediaCreate.memberId())
                .plantCollection(new ArrayList<>())
                .build();
    }

    public void verifyOwner(Passport passport) {
        if (!passport.memberId().equals(memberId)) {
            throw new CommonException("도감 소유자가 아닙니다.", HttpStatus.FORBIDDEN.value());
        }
    }
}
