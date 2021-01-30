package dev.bluvolve.kafka.errorresistantconsumer.patientNameChanged;

import dev.bluvolve.kafka.errorresistantconsumer.processing.EventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientNameChangedEventProcessor extends EventProcessor<PatientNameChangedEventService> {

    private static final String TOPIC = "patientNameChanged";

    @Autowired
    public PatientNameChangedEventProcessor(PatientNameChangedEventService service){
        super(TOPIC);
        this.service = service;
    }
}
