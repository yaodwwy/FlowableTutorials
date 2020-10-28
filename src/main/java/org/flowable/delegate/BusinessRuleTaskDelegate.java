package org.flowable.delegate;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;

@Slf4j
public class BusinessRuleTaskDelegate implements org.flowable.engine.delegate.BusinessRuleTaskDelegate {
    @Override
    public void addRuleVariableInputIdExpression(Expression inputId) {
        log.info("addRuleVariableInputIdExpression: {} ",inputId);
    }

    @Override
    public void addRuleIdExpression(Expression inputId) {
        log.info("addRuleIdExpression: {} ",inputId);

    }

    @Override
    public void setExclude(boolean exclude) {
        log.info("exclude: {} ",exclude);

    }

    @Override
    public void setResultVariable(String resultVariableName) {
        log.info("resultVariableName: {} ",resultVariableName);
    }

    @Override
    public void execute(DelegateExecution execution) {
        log.info("execution: {} ",execution);
    }
}
