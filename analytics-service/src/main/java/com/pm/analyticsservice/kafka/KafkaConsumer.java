package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics-group")
    public void consumeEvent(byte[] eventBytes) {
        // Logic to deserialize the eventBytes and process the event
        // For example, you might use Protobuf or JSON deserialization here
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(eventBytes);
            log.info("Received patient event: [PatientId={}, PatientName={}, PatientEmail={}, EventType={}]",
                    patientEvent.getPatientId(), patientEvent.getName(), patientEvent.getEmail(), patientEvent.getEventType());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing event {} ", e.getMessage());
        }
    }
}
