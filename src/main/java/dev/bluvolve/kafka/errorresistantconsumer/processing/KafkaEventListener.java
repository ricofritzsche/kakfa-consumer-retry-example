package dev.bluvolve.kafka.errorresistantconsumer.processing;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
public class KafkaEventListener {
    private final RetryPublisher retryPublisher;

    private final List<IEventProcessor> processors;

    @Value("${kafka.consumer.group}")
    private String groupId;

    @Autowired
    public KafkaEventListener(List<IEventProcessor> processors, RetryPublisher retryPublisher) {

        this.processors = processors;
        this.retryPublisher = retryPublisher;
    }

    @KafkaListener(topics = "#{'${kafka.consumer.topic}'.split(',')}", groupId = "${kafka.consumer.group}")
    public void onEventReceived(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        log.info("Received event from topic: {}", record.topic());
        log.info("Offset is at: {}", record.offset());
        log.debug("Payload: {}", record.value());

        String topic = record.topic();
        if(record.topic().indexOf("_") > 0){
            topic = record.topic().substring(0, record.topic().indexOf("_"));
        }

        String finalTopic = topic;
        Optional<IEventProcessor> processor = this.processors.stream().filter(p -> p.topic().toLowerCase().equals(finalTopic.toLowerCase())).findFirst();

        try{
            ProcessingResult result = processor.get().processEvent(record);

            if(result.getStatus() == ProcessingResultEnum.EventFilterResponseStatus.SUCCESS){
                log.debug(result.getMessage());
            }else{
                log.warn(result.getMessage());
            }

            log.debug("Successfully processed...");
            acknowledgment.acknowledge();

        }catch(Exception ex){
            String retryTopic = topic + "_" + this.groupId + "_retry";
            IEvent event = processor.get().getEvent(record);
            this.retryPublisher.publishEvent((RetryableEvent) event, retryTopic);

            log.debug("message {} forwarded to retry topic {}", event, retryTopic);
            acknowledgment.acknowledge();
        }

    }
}