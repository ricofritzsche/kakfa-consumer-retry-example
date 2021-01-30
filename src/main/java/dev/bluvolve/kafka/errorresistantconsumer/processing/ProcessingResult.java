package dev.bluvolve.kafka.errorresistantconsumer.processing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event Filter Response.
 * Describes the response of the filter service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingResult {
    public static final String EVENT_PROCESSED_BY_BACKEND_SERVICE = "Event processed by backend service";
    public static final String EVENT_NOT_RELEVANT_FOR_BACKEND_SERVICE = "Event is not relevant for the backend service";

    private ProcessingResultEnum.EventFilterResponseStatus status;
    private String message;

    /**
     * Creates a default response for a successful event processing.
     * @return Event Filter Response instance.
     */
    public static ProcessingResult createSuccessResponse(){
        return new ProcessingResult(ProcessingResultEnum.EventFilterResponseStatus.SUCCESS, EVENT_PROCESSED_BY_BACKEND_SERVICE);
    }

    /**
     * Creates a default response in case event is not relevant for the backend service.
     * @param reason explicit reason why no action required.
     * @return Event Filter Response instance.
     */
    public static ProcessingResult createNoActionRequiredResponse(String reason){
        return new ProcessingResult(ProcessingResultEnum.EventFilterResponseStatus.NO_ACTION_REQUIRED, reason);
    }

    /**
     * Creates an event filter response for unprocessable events.
     * @param errorMessage the error message.
     * @return Event Filter Response instance.
     */
    public static ProcessingResult createUnprocessableResponse(String errorMessage){
        return new ProcessingResult(ProcessingResultEnum.EventFilterResponseStatus.UNPROCESSABLE, errorMessage);
    }
}
