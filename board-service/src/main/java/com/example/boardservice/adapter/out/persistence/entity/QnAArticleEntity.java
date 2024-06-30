package com.example.boardservice.adapter.out.persistence.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("qna_board")
@Table(name = "qna_board")
public class QnAArticleEntity extends CommonArticleEntity {}
