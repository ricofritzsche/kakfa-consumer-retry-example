package dev.bluvolve.kafka.errorresistantconsumer.processing;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Slf4j
public abstract class EventProcessor<T extends IDomainService> implements IEventProcessor {
    protected T service;
    protected final String topic;

    protected EventProcessor(String topic) {
        this.topic = topic;
    }

    @Override
    public ProcessingResult processEvent(ConsumerRecord record) {

        try{
            IEvent event = this.service.convertConsumerRecord(record);
            return this.service.processEvent(event);

        }catch(JsonProcessingException ex){
            log.error(ex.getMessage());
            return ProcessingResult.createUnprocessableResponse(ex.getMessage());
        }
    }

    public IEvent getEvent(ConsumerRecord record){
        try{
            return this.service.convertConsumerRecord(record);
        }catch(JsonProcessingException ex){
            log.error(ex.getMessage());
            return null;
        }

    }

    @Override
    public String topic() {
        return this.topic.toLowerCase();
    }
}
