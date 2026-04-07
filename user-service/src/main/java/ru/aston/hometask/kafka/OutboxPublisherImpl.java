package ru.aston.hometask.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

import jakarta.transaction.Transactional;
import ru.aston.hometask.repository.OutboxRepository;
import ru.aston.hometask.repository.model.OutboxEvent;

@Component
public class OutboxPublisherImpl implements OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OutboxPublisherImpl.class);

    public OutboxPublisherImpl(
            OutboxRepository outboxRepository,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void publish() {
    	logger.info("Outbox publisher running, checking for unsent events...");
        List<OutboxEvent> events = outboxRepository.findTop20BySentFalseOrderByCreatedAtAsc();
        logger.info("Found {} unsent events", events.size());

        for (int i = 0; i < events.size(); i++) {
            OutboxEvent event = events.get(i);
            try {
                kafkaTemplate.send(event.getTopic(), event.getPayload()).get();
                event.setSent(true);

            } catch (ExecutionException e) {
                logger.error("Failed to send event to Kafka: " + event.getPayload(), e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Interrupted while sending event", e);
                break;
            }
        }
    }
}
