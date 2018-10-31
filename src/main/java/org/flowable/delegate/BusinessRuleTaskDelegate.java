package org.flowable.delegate;

import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;

public class BusinessRuleTaskDelegate implements org.flowable.engine.delegate.BusinessRuleTaskDelegate {
    @Override
    public void addRuleVariableInputIdExpression(Expression inputId) {
        System.out.println(inputId.getExpressionText());
        System.out.println("addRuleVariableInputIdExpression");
    }

    @Override
    public void addRuleIdExpression(Expression inputId) {
        System.out.println("addRuleIdExpression");

    }

    @Override
    public void setExclude(boolean exclude) {
        System.out.println("exclude");

    }

    @Override
    public void setResultVariable(String resultVariableName) {

        System.out.println("resultVariableName");
    }

    @Override
    public void execute(DelegateExecution execution) {

        System.out.println("execution");
    }
}
