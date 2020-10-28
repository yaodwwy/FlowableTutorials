package cn.adbyte.flowable.standalone;

import org.flowable.engine.*;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.history.HistoricDetail;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Import({FlowableStandalone.class})
public class _12表单Test {

    @Autowired
    private FlowableFactory factory;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private FormService formService;

    @Autowired
    private HistoryService historyService;


    @Test
    public void 内置表单() throws FileNotFoundException {

        List<ProcessDefinition> processDefinitions = factory.deploy("processes/_12内置表单.bpmn20.xml");
        ProcessDefinition processDefinition = processDefinitions.get(0);
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        assertNull(startFormData.getFormKey());

        Map<String, String> formProperties = new HashMap<String, String>();
        formProperties.put("name", "HenryYan");
        /**
         * 流程实例是通过提交表单来启动
         */
        ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), formProperties);
        assertNotNull(processInstance);

        // 运行时变量
        Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
        assertEquals(variables.size(), 1);
        Set<Map.Entry<String, Object>> entrySet = variables.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

        // 历史记录
        List<HistoricDetail> list = historyService.createHistoricDetailQuery().formProperties().processInstanceId(processInstance.getId()).list();
        assertEquals(1, list.size());

        // 获取第一个任务节点
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        assertEquals("First Step", task.getName());

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        assertNotNull(taskFormData);
        assertNull(taskFormData.getFormKey());
        List<FormProperty> taskFormProperties = taskFormData.getFormProperties();
        assertNotNull(taskFormProperties);
        for (FormProperty formProperty : taskFormProperties) {
            System.out.println(ToStringBuilder.reflectionToString(formProperty));
        }
        formProperties = new HashMap<String, String>();
        formProperties.put("setInFirstStep", "01/12/2012");
        /**
         * 提交表单并完成任务
         */
        formService.submitTaskFormData(task.getId(), formProperties);

        // 获取第二个节点
        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskName("Second Step").singleResult();
        assertNotNull(task);
        taskFormData = formService.getTaskFormData(task.getId());
        assertNotNull(taskFormData);
        List<FormProperty> formProperties2 = taskFormData.getFormProperties();
        for (FormProperty formProperty : formProperties2) {
            System.out.println(formProperty.getName() + " = " + formProperty.getValue());
        }
        assertNotNull(formProperties2);
        assertEquals(2, formProperties2.size());
        assertNotNull(formProperties2.get(0).getValue());
        assertEquals(formProperties2.get(0).getValue(), "01/12/2012");

        variables.put("age", "15");
        taskService.complete(task.getId(), variables);

        long count = taskService.createTaskQuery().processInstanceId(processInstance.getId()).count();
        assertEquals(count, 0);
    }

    @Test
//    @Deployment(resources = { "processes/_12外置表单.bpmn20.xml", "form/start.form", "form/first-step.form.html" ,"form/second-step.form"})
    public void 外置表单() {
        factory.deploy("processes/_12外置表单.bpmn20.xml",
                "form/start.form.html", "form/first-step.form.html", "form/second-step.form.html");
        // Get start form
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("FormKey").latestVersion().singleResult();
        String procDefId = processDefinition.getId();
        Object startForm = formService.getRenderedStartForm(procDefId);
        assertNotNull(startForm);
        System.out.println("表单内容：" + startForm);
        assertEquals("<input id=\"start-name\" />", startForm);

        Map<String, String> formProperties = new HashMap<String, String>();
        formProperties.put("startName", "HenryYan");
        System.out.println("提交【开始】表单属性..." + formProperties);
        ProcessInstance processInstance = formService.submitStartFormData(procDefId, formProperties);
        assertNotNull(processInstance);

        Task task = taskService.createTaskQuery().taskAssignee("user1").singleResult();
        Object renderedTaskForm = formService.getRenderedTaskForm(task.getId());
        System.out.println("获取表单内容：" + renderedTaskForm);
        assertEquals("<input id=\"start-name\" value=\"HenryYan\" />\n<input id=\"first-name\" />", renderedTaskForm);

        formProperties = new HashMap<String, String>();
        formProperties.put("firstName", "kafeitu");
        System.out.println("提交【完成任务】表单属性..." + formProperties);
        formService.submitTaskFormData(task.getId(), formProperties);

        task = taskService.createTaskQuery().taskAssignee("user2").orderByTaskCreateTime().desc().list().get(0);
        assertNotNull(task);
        renderedTaskForm = formService.getRenderedTaskForm(task.getId());
        System.out.println("获取表单内容：" + renderedTaskForm);
        assertEquals("<input id=\"first-name\" value=\"kafeitu\" />", renderedTaskForm);
    }
}
