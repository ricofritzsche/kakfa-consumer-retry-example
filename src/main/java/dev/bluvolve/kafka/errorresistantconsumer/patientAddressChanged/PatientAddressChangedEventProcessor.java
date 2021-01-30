package dev.bluvolve.kafka.errorresistantconsumer.patientAddressChanged;

import dev.bluvolve.kafka.errorresistantconsumer.processing.EventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientAddressChangedEventProcessor extends EventProcessor<PatientAddressChangedEventFilterService> {

    private static final String TOPIC = "patientAddressChanged";

    @Autowired
    public PatientAddressChangedEventProcessor(PatientAddressChangedEventFilterService service){
        super(TOPIC);
        this.service = service;
    }
}
