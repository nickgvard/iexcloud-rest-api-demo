package my.education.iexcloudrestapidemo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.education.iexcloudrestapidemo.dto.UserDto;
import my.education.iexcloudrestapidemo.dto.UserRegistrationDto;
import my.education.iexcloudrestapidemo.service.GenericKafkaProducerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerUserServiceImpl implements GenericKafkaProducerService<UserRegistrationDto> {

    @Value("${kafka.registration.topic.name}")
    private String registrationTopic;
    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void produce(UserRegistrationDto userDto) {
        try {
            String jsonUser = objectMapper.writeValueAsString(userDto);
            ListenableFuture<SendResult<Long, String>> produceFuture
                    = kafkaTemplate.send(registrationTopic, jsonUser);
            produceFuture.addCallback(
                    result -> log.info("[Sent message by email: {} with offset={}]", userDto.getEmail(), result.getRecordMetadata().offset()),
                    error -> log.error("[Unable to sent message by email: {}]. Exception: {}", userDto.getEmail(), error.getMessage()));
            kafkaTemplate.flush();
        }catch (JsonProcessingException processingException) {
            log.error("[Json processing by user {} failed.]", userDto);
        }
    }
}
