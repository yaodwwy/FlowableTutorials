package org.flowable.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;

/**
 * @author Adam
 */
@Slf4j
public class MyJobEventListener implements FlowableEventListener {
    @Override
    public void onEvent(FlowableEvent event) {
        if (event.getType() == FlowableEngineEventType.JOB_EXECUTION_SUCCESS) {
            log.debug("A job well done!");
        } else if (event.getType() == FlowableEngineEventType.JOB_EXECUTION_FAILURE) {
            log.debug("A job has failed...");
        } else {
            log.debug("Event received:{} ", event.getType());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
