package dev.bluvolve.kafka.errorresistantconsumer.processing;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IEventProcessor {
    ProcessingResult processEvent(ConsumerRecord record);
    IEvent getEvent(ConsumerRecord record);
    String topic();
}
