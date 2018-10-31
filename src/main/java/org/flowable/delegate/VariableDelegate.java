package org.flowable.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Map;

public class VariableDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("=============变量输出类=============");
        Map<String, Object> variables = execution.getVariables();
        System.out.println(variables);
    }
}
