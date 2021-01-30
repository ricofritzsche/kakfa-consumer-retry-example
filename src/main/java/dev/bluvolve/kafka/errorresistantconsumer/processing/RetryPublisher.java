package dev.bluvolve.kafka.errorresistantconsumer.processing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Generic functions.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RetryPublisher {
    private final KafkaTemplate<String, RetryableEvent> kafkaTemplate;

    /**
     * Publishes the given event on a kafka message broker.
     * @param event given event.
     */
    public void publishEvent(RetryableEvent event, String topic){
        log.debug("Event of type {} received", event.getClass().getSimpleName());
        ProducerRecord<String, RetryableEvent> producerRecord = new ProducerRecord<>(topic, event);
        producerRecord.headers().add("retries", ByteBuffer.allocate(4).putInt(event.increaseRetries()).array());

        ListenableFuture<SendResult<String, RetryableEvent>> future = this.kafkaTemplate.send(producerRecord);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, RetryableEvent> result) {
                log.info("sent message='{}' with offset={}", event, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("unable to send message='{}'", event, ex);
            }
        });

    }
}
