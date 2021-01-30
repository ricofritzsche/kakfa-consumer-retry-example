package dev.bluvolve.kafka.errorresistantconsumer.processing;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Business interface represents Domain Services operations.
 */
public interface IDomainService<T extends IEvent> {
    /**
     * Handles the consumed "SomethingChangedEvent".
     * @param event An instance of the event.
     * @return the result object.
     */
    ProcessingResult processEvent(T event);

    /**
     * Create instance of T from requested record.
     * @param record the event.
     * @return The instance for type T.
     * @throws JsonProcessingException exception.
     */
    T convertConsumerRecord(ConsumerRecord<String, String> record) throws JsonProcessingException;
}
