package ru.analytical.storage.receiver.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class UserEventsProducerConfig {
    private final String topicName;
    private final int numPartitions;
    private final short replicationFactor;
    private final int lingerMsConfig;
    private final KafkaProperties kafkaProperties;

    public UserEventsProducerConfig(@Value("${kafka.topics.receiver-service.name}") String topicName,
                                    @Value("${kafka.topics.receiver-service.numPartitions}") int numPartitions,
                                    @Value("${kafka.topics.receiver-service.replicationFactor}") short replicationFactor,
                                    @Value("${spring.kafka.producer.linger-ms-config}") int lingerMsConfig,
                                    KafkaProperties kafkaProperties) {
        this.topicName = topicName;
        this.numPartitions = numPartitions;
        this.replicationFactor = replicationFactor;
        this.lingerMsConfig = lingerMsConfig;
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public NewTopic userEventsTopic() {
        return new NewTopic(topicName, numPartitions, replicationFactor);
    }

    @Bean
    public Map<String, Object> userEventsProducerConfigs() {
        var properties =  kafkaProperties.buildProducerProperties();
        properties.put(ProducerConfig.LINGER_MS_CONFIG, lingerMsConfig);

        return properties;
    }

    @Bean
    public ProducerFactory<String, Object> userEventsProducerFactory() {
        return new DefaultKafkaProducerFactory<>(userEventsProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> userEventsKafkaTemplate() {
        var kafkaTemplate = new KafkaTemplate<>(userEventsProducerFactory());
        kafkaTemplate.setDefaultTopic(topicName);

        return kafkaTemplate;
    }
}
