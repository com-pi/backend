package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.request.DiaryArticleRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "board-service", url = "${feign.client.config.diary-article.url}")
public interface DiaryArticleClient {
    @PostMapping("/diaries")
    void postDiaryArticle(
            @RequestBody DiaryArticleRequest request
    );

}
