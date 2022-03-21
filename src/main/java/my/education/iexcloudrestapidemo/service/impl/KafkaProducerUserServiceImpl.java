package my.education.iexcloudrestapidemo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.education.iexcloudrestapidemo.dto.UserDto;
import my.education.iexcloudrestapidemo.service.GenericKafkaProducerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerUserServiceImpl implements GenericKafkaProducerService<UserDto> {

    @Value("${kafka.registration.topic.name}")
    private String registrationTopic;
    private final KafkaTemplate<Long, UserDto> kafkaTemplate;

    @Override
    public void produce(UserDto userDto) {
        ListenableFuture<SendResult<Long, UserDto>> produceFuture
                = kafkaTemplate.send(registrationTopic, userDto);
        produceFuture.addCallback(
                result -> log.info("[Sent message by email: {} with offset={}]", userDto.getEmail(), result.getRecordMetadata().offset()),
                error -> log.error("[Unable to sent message by email: {}]. Exception: {}", userDto.getEmail(), error.getMessage()));
        kafkaTemplate.flush();
    }
}
