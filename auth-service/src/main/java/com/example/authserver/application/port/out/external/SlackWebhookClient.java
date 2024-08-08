package com.example.authserver.application.port.out.external;

import com.example.authserver.domain.SlackMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slackWebhookClient", url = "https://hooks.slack.com/services/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")
public interface SlackWebhookClient {

    @PostMapping
    void sendSlackMessage(@RequestBody SlackMessage message);

}