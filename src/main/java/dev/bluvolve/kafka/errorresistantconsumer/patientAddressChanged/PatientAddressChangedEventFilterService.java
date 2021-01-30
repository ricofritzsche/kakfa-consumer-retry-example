package dev.bluvolve.kafka.errorresistantconsumer.patientAddressChanged;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.bluvolve.kafka.errorresistantconsumer.processing.IDomainService;
import dev.bluvolve.kafka.errorresistantconsumer.processing.ProcessingResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

/**
 * {@inheritDoc}
 */
@Service
@Slf4j
public class PatientAddressChangedEventFilterService implements IDomainService<PatientAddressChangedEvent> {

    /**
     * Handles the Event. Checks if the Service is responsible for the given event.
     * @param event incoming data.
     * @return an instance of the sent data object, or NULL in case it is not processable.
     */
    @SneakyThrows
    @Override
    public ProcessingResult processEvent(PatientAddressChangedEvent event) {
        // Logic here
        return ProcessingResult.createSuccessResponse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientAddressChangedEvent convertConsumerRecord(ConsumerRecord<String, String>  record) throws JsonProcessingException {
        String payload = record.value();
        PatientAddressChangedEvent event = PatientAddressChangedEvent.fromStringMessage(payload);

        log.info("Try to process address changed event of patient with id {}", event.getId());
        return event;
    }
}
