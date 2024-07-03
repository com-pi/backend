package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.application.port.out.CommonArticleCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonArticleCommandAdapter implements CommonArticleCommandPort {

    private final CommonArticleRepository commonArticleRepository;

}
