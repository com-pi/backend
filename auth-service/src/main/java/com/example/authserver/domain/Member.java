package com.example.authserver.domain;

import com.example.common.BaseTimeAbstractEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.geo.Point;

@Entity
@Table(name = "MEMBER")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(exclude = {"location"})
public class Member extends BaseTimeAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kakao_id", unique = true)
    private String kakaoId;

    @Column(name = "naver_id", unique = true)
    private String naver_id;

    private String email;
    private String password;
    private String nickname;
    private String image_url;
    private String thumbnail_url;

    @Column(columnDefinition = "Point")
    private Point location;

}
