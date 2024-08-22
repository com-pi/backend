package com.example.authserver.adapter.in;

import com.example.authserver.adapter.in.request.MemberCreateRequest;
import com.example.authserver.adapter.out.entity.EventRecordEntity;
import com.example.authserver.adapter.out.repository.EventRecordJpaRepository;
import com.example.authserver.event.MemberEventHandler;
import com.example.authserver.event.MemberEventPublisher;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.configuration.KafkaTopic;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class JoinControllerTest {

    @MockBean
    RedisPort redisPort;

    @MockBean
    MemberEventPublisher memberEventPublisher;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventRecordJpaRepository eventRecordJpaRepository;

    @MockBean
    MemberEventHandler memberEventHandler;

    private final BlockingQueue<ConsumerRecord<String, String>> records = new LinkedBlockingQueue<>();

    @KafkaListener(topics = KafkaTopic.MEMBER_CQRS, groupId = "test")
    public void listen(ConsumerRecord<String, String> consumerRecord) {
        records.add(consumerRecord);
    }

    @Test
    @Disabled
    void test() throws Exception {
        // given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .email("kihong@gmail.com")
                .password("1q2w3e4r5")
                .phoneNumber("01012345678")
                .build();

        given(redisPort.checkVerification(any(), any())).willReturn(true);

        // when
        // then
        mockMvc.perform(post("/join")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ConsumerRecord<String, String> record = records.poll(10, TimeUnit.SECONDS);
        then(memberEventPublisher).should().publishEvent(any());
        List<EventRecordEntity> all = eventRecordJpaRepository.findAll();
        System.out.println("all = " + all);
        assertThat(record).isNotNull();
        System.out.println("record = " + record);
    }

}