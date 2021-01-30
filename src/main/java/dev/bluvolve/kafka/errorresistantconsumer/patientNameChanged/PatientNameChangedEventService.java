package dev.bluvolve.kafka.errorresistantconsumer.patientNameChanged;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.bluvolve.kafka.errorresistantconsumer.patientAddressChanged.PatientAddressChangedEvent;
import dev.bluvolve.kafka.errorresistantconsumer.processing.IDomainService;
import dev.bluvolve.kafka.errorresistantconsumer.processing.ProcessingResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeoutException;

/**
 * {@inheritDoc}
 */
@Service
@Slf4j
public class PatientNameChangedEventService implements IDomainService<PatientNameChangedEvent> {

    /**
     * Handles the Event. Checks if the Service is responsible for the given event.
     * @param event incoming data.
     * @return an instance of the sent data object, or NULL in case it is not processable.
     */
    @SneakyThrows
    @Override
    public ProcessingResult processEvent(PatientNameChangedEvent event) {
        if(event.getRetries() < 3 && event.getName().contains("throw")){
            throw new TimeoutException();
        }

        return ProcessingResult.createSuccessResponse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientNameChangedEvent convertConsumerRecord(ConsumerRecord<String, String>  record) throws JsonProcessingException {
        String payload = record.value();

        int retries = 0;
        for (Header header : record.headers()) {
            if (header.key().equals("retries") ) {
                byte[] bytes = header.value();
                retries = ByteBuffer.wrap(bytes).getInt();
            }
        }

        PatientNameChangedEvent event = PatientNameChangedEvent.fromStringMessage(payload);
        event.setRetries(retries);
        log.info("Try to process address changed event of patient with id {}", event.getId());
        return event;
    }
}
