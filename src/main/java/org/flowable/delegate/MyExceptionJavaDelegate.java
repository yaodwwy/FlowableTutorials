package org.flowable.delegate;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
@Slf4j
public class MyExceptionJavaDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.debug("[MyExceptionJavaDelegate：{}]", delegateExecution);
        throw new RuntimeException("MyExceptionJavaDelegate 百分百被抛出！");
    }
}
