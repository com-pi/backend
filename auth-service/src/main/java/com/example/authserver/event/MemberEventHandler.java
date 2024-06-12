package com.example.authserver.event;

import com.example.authserver.adapter.out.command.KafkaMessageProducer;
import com.example.authserver.application.EventRecordService;
import com.example.authserver.configuration.KafkaTopic;
import com.example.authserver.domain.Event;
import com.example.authserver.domain.EventRecord;
import com.example.authserver.domain.EventStatus;
import com.example.authserver.domain.Member;
import com.example.common.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberEventHandler {

    private final EventRecordService eventRecordService;
    private final KafkaMessageProducer kafkaMessageProducer;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleMemberEvent(Event<Member> event) {
        eventRecordService.recordEvent(event);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransaction(Event<Member> event) {
        EventRecord<?> foundEvent = eventRecordService.findByTransactionId(
                        event.getTransactionId(), Member.class)
                .orElseThrow(() -> new InternalServerException("이벤트를 찾을 수 없습니다.", null));

        if(EventStatus.PUBLISHED.equals(foundEvent.status())) {
            return;
        }

        kafkaMessageProducer.sendMessage(KafkaTopic.MEMBER_CQRS, event);
        eventRecordService.markAsPublished(foundEvent.id());
    }

}
