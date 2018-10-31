package org.flowable.delegate._6中间抛出事件补偿;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class IcbcReduceDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution arg0) {
        System.out.println("工商银行扣款");
    }

}
