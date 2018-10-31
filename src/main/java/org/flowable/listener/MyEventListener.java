package org.flowable.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;

/**
 * 参考 BaseEntityEventListener
 * @author Adam
 */
@Slf4j
public class MyEventListener implements FlowableEventListener {
    @Override
    public void onEvent(FlowableEvent event) {
        if(event.getType() == FlowableEngineEventType.JOB_EXECUTION_SUCCESS) {
            log.debug("A job well done!");
        } else if (event.getType() == FlowableEngineEventType.JOB_EXECUTION_FAILURE) {
            log.debug("A job has failed...");
        } else {
            log.debug("Event received: " + event.getType());
        }
    }

    /**
     * 调度事件时抛出异常时的行为。返回false时，将忽略该异常。
     */
    @Override
    public boolean isFailOnException() {
        return false;
    }

    /**
     * 是否触发事务生命周期事件。支持的事务生命周期事件的值是：COMMITTED，ROLLED_BACK，COMMITTING，ROLLINGBACK
     */
    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
