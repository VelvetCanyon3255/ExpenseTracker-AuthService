package org.example.eventProducer;

import jakarta.annotation.PostConstruct;
import org.example.model.UserInfoDto;
import org.example.model.UserInfoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer {

    private final KafkaTemplate<String, UserInfoEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;



    @Autowired
    public UserInfoProducer(KafkaTemplate<String, UserInfoEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void senEventToKafka(UserInfoEvent userInfoEvent) {
        Message<UserInfoEvent> message = MessageBuilder.withPayload(userInfoEvent)
                                        .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME).
                                        build();
        System.out.println("Sending event: " + userInfoEvent);
        kafkaTemplate.send(message);
    }
}
