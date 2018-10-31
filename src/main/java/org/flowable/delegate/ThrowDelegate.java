package org.flowable.delegate;

import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class ThrowDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution arg0) {
        System.out.println("主流程即将抛出异常 触发Event sub Process");
        throw new BpmnError("errorCode 400");
    }

}
