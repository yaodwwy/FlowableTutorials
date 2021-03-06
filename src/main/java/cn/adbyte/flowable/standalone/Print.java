package cn.adbyte.flowable.standalone;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Print {

    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void tasks(Task tasks) {
        ArrayList<Task> t = new ArrayList<>();
        t.add(tasks);
        Print.tasks(t);
    }

    public static void tasks(List<Task> tasks) {
        for (Task task : tasks) {
            log.debug("================ 当前流程任务 ==============");
            log.debug("[Task：{}] [name：{}] [owner：{}] [assignee：{}] [executionId：{}] [TaskLocalVariables：{}] " +
                            "[ProcessVariables：{}] [DelegationState：{}]",
                    task.getId(), task.getName(), task.getOwner(), task.getAssignee(), task.getExecutionId()
                    , task.getTaskLocalVariables(), task.getProcessVariables(), task.getDelegationState());
        }
    }

    public static void instances(ProcessInstance instances) {
        ArrayList<ProcessInstance> instances1 = new ArrayList<>();
        instances1.add(instances);
        Print.instances(instances1);
    }

    public static void instances(List<ProcessInstance> instances) {
        for (ProcessInstance pi : instances) {
            log.debug("=============== 当前流程实例 ==============");
            log.debug("[ProcessInstance：{}] [name：{}] [BusinessKey：{}] [ProcessVariables：{}] [StartTime：{}] " +
                            "[StartUserId：{}] [Description：{}] [ProcessDefinitionName：{}] [ProcessDefinitionKey：{}]",
                    pi.getId(), pi.getName(), pi.getBusinessKey(), pi.getProcessVariables(),
                    sdf.format(pi.getStartTime()), pi.getStartUserId(), pi.getDescription(),
                    pi.getProcessDefinitionName(), pi.getProcessDefinitionKey());
        }
    }

    public static void exec(Execution executions) {
        ArrayList<Execution> list = new ArrayList<>();
        list.add(executions);
        Print.exec(list);
    }

    public static void exec(List<Execution> executions) {
        for (Execution e : executions) {
            log.debug("=============== 当前执行流节点 ==============");

            log.debug("[Execution：{}] [name：{}] [ActivityId：{}] [ParentId：{}] [ProcessInstanceId：{}] " +
                            "[RootProcessInstanceId：{}] [Description：{}] [isEnded：{}]",
                    e.getId(), e.getName(), e.getActivityId(), e.getParentId(), e.getProcessInstanceId(),
                    e.getRootProcessInstanceId(), e.getDescription(),e.isEnded());
        }
    }
}
