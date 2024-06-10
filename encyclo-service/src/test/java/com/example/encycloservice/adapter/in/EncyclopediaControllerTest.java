package com.example.encycloservice.adapter.in;

import com.example.encycloservice.adapter.in.fake.FakeEncyclopediaCommand;
import com.example.encycloservice.adapter.in.fake.TestConfig;
import com.example.encycloservice.adapter.out.external.SearchResultByScraper;
import com.example.encycloservice.application.port.out.EncyclopediaCommand;
import com.example.encycloservice.application.port.out.ScraperPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
@ActiveProfiles("local")
class EncyclopediaControllerTest {

    @MockBean
    ScraperPort fakeScraperPort;

    @Autowired
    EncyclopediaCommand fakeEncyclopediaCommand;

    @Autowired
    MockMvc mockMvc;

    @Test
    void sync_디비_동기화_요청_비동기_처리_성공() throws Exception {
        // given
        var r1 = SearchResultByScraper.SearchPlantResult.builder()
                .id("장미")
                .name("장미")
                .thumbnailUrl("null")
                .build();

        var r2 = SearchResultByScraper.SearchPlantResult.builder()
                .id("장미2")
                .name("장미2")
                .thumbnailUrl("null")
                .build();

        SearchResultByScraper result = SearchResultByScraper.builder()
                .results(List.of(r1, r2))
                .build();

        BDDMockito.given(fakeScraperPort.searchPlant("장미")).willReturn(result);

        mockMvc.perform(post("/sync")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("keyword", "장미"))
                .andExpect(
                        MockMvcResultMatchers.status().is(200)
                );
        FakeEncyclopediaCommand fake = (FakeEncyclopediaCommand) fakeEncyclopediaCommand;

        Assertions.assertThat(fake.ids.size()).isEqualTo(2);
        Assertions.assertThat(fake.ids.containsAll(List.of(new String[]{"장미", "장미2"}))).isTrue();
    }

}