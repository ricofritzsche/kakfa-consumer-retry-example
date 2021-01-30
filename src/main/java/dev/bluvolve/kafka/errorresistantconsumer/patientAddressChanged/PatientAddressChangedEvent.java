package dev.bluvolve.kafka.errorresistantconsumer.patientAddressChanged;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bluvolve.kafka.errorresistantconsumer.processing.IEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Describes the incoming data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientAddressChangedEvent implements IEvent {
    @NonNull
    private String id;
    @NonNull
    private String street;
    @NonNull
    private String city;

    public static PatientAddressChangedEvent fromStringMessage(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, PatientAddressChangedEvent.class);
    }
}
