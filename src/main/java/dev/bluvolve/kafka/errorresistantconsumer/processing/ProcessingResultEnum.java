package dev.bluvolve.kafka.errorresistantconsumer.processing;

public class ProcessingResultEnum {
    public enum EventFilterResponseStatus {
        /**
         * The processing was successful. It means that the received message will be acknowledged.
         */
        SUCCESS,

        /**
         * No action required. It means that the received message will be acknowledged.
         */
        NO_ACTION_REQUIRED,

        /**
         * The incoming event can not be processed because the format is wrong.
         */
        UNPROCESSABLE,
    }
}
