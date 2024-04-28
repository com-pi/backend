package com.example.boardservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String imageUrl;

    private User(Long id) {
        this.id = id;
    }

    public static User ofId(Long authorId){
        return new User(authorId);
    }

}
