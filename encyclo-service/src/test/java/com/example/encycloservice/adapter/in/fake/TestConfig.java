package com.example.encycloservice.adapter.in.fake;

import com.example.encycloservice.application.port.out.EncyclopediaCommand;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public EncyclopediaCommand encyclopediaCommand() {
        return new FakeEncyclopediaCommand();
    }

}
