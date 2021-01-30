package dev.bluvolve.kafka.errorresistantconsumer.processing;

import lombok.Getter;

@Getter
public abstract class RetryableEvent implements IEvent{

    private int retries = 0;

    public void setRetries(int retries){
        this.retries = retries;
    }

    public int increaseRetries(){
        this.retries += 1;
        return this.retries;
    }
}
