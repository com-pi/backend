package com.example.boardservice.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hashtags")
@Tag(name = "해시태그", description = "해시태그 API")
public class HashtagController {

}
