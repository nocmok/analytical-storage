package ru.analytical.storage.receiver.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.analytical.storage.receiver.dto.UserEvent;

@Slf4j
@Component
public class UserEventsProducer {
    private final String userEventsTopicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserEventsProducer(@Value("${kafka.topics.receiver-service.name}") String userEventsTopicName,
                              KafkaTemplate<String, Object> kafkaTemplate) {
        this.userEventsTopicName = userEventsTopicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserEvent(String key, UserEvent body) {
        kafkaTemplate.send(userEventsTopicName, key, body);
        log.debug("Message {} was sent to Kafka", body);
    }
}
