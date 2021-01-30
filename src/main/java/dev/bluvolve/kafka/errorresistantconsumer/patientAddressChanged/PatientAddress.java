package dev.bluvolve.kafka.errorresistantconsumer.patientAddressChanged;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientAddress {
    private String id;
    private String street;
    private String city;
}
