package dev.bluvolve.kafka.errorresistantconsumer.patientNameChanged;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bluvolve.kafka.errorresistantconsumer.patientAddressChanged.PatientAddressChangedEvent;
import dev.bluvolve.kafka.errorresistantconsumer.processing.IEvent;
import dev.bluvolve.kafka.errorresistantconsumer.processing.RetryableEvent;
import lombok.*;

import java.util.UUID;

/**
 * Describes the incoming data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientNameChangedEvent extends RetryableEvent {

    @NonNull
    private String id;
    @NonNull
    private String name;

    public static PatientNameChangedEvent fromStringMessage(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, PatientNameChangedEvent.class);
    }
}
