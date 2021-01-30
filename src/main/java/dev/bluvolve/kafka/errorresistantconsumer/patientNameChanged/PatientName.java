package dev.bluvolve.kafka.errorresistantconsumer.patientNameChanged;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientName {
    private String id;
    private String name;
}
