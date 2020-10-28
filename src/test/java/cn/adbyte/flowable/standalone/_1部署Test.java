package cn.adbyte.flowable.standalone;

import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 流程文件部署
 * addClasspathResource
 * addInputStream
 * addString
 * addZipInputStream
 * addBpmnModel
 * addBytes
 * deploy
 *
 * 流程定义的删除行为：
 * 1. 不管是否指定级联，都会删除部署相关的身份数据、流程定义数据、流程资源与部署数据。
 * 2. 如果设置为级联删除，则会将运行的流程实例、流程任务以及流程实例的历史数据删除。
 * 3. 如果不级联删除，但是存在运行时数据，例如还有流程实例，就会删除失败。
 */
@Slf4j
@RunWith(SpringRunner.class)
@Import({FlowableStandalone.class})
public class _1部署Test {

    @Autowired
    private FlowableFactory factory;

    @Test
    public void 部署文本文件() throws IOException {
        List<ProcessDefinition> deploy = factory.deploy("deploy/my_text.txt");
        // 数据查询
        InputStream inputStream = factory.getResourceAsStream(deploy.get(0).getId(), "deploy/my_text.txt");
        int count = inputStream.available();
        byte[] contents = new byte[count];
        inputStream.read(contents);
        String result = new String(contents);
        //输入结果
        log.debug("部署文本文件: " + result);
    }

    /**
     * 自动解压部署压缩包里的文件
     * @throws FileNotFoundException
     */
    @Test
    public void 部署Zip文件() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File("src/main/resources/deploy/datas.zip"));
        ZipInputStream zis = new ZipInputStream(fis);
        Deployment deploy = factory.deploy(zis);
        log.debug("部署Zip文件完成，可在数据库中查看" + deploy.getId() + ", " + deploy.getName());
    }


    @Test
    public void 构建BPMN模型() throws Exception {
        // 创建BPMN模型对象
        BpmnModel bpmnModel = new BpmnModel();
        // 创建一个流程定义
        Process process = new Process();
        bpmnModel.addProcess(process);
        process.setId("firstProcess"); //processKey
        process.setName("My firstProcess");

        // 创建Flow元素（所有的事件、任务都被认为是Flow）
        process.addFlowElement(createStartEvent());
        process.addFlowElement(createUserTask("task1", "First task", "张三"));
        process.addFlowElement(createEndEvent());
        process.addFlowElement(createSequenceFlow("start", "task1"));
        process.addFlowElement(createSequenceFlow("task1", "end"));

        //自动布局
        new BpmnAutoLayout(bpmnModel).execute();
        factory.buildBpmn("first", bpmnModel,"firstProcess", true);
    }

    @Test
    public void 生成PNG() {
        factory.generatePng("scope.bpmn20.xml");
    }

    @Test
    public void 动态部署() throws Exception {
        // 1. 创建一个空的BpmnModel和Process对象
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId("my-process");

        // 创建Flow元素（所有的事件、任务都被认为是Flow）
        process.addFlowElement(createStartEvent());
        process.addFlowElement(createUserTask("task1", "First task", "fred"));
        process.addFlowElement(createUserTask("task2", "Second task", "john"));
        process.addFlowElement(createEndEvent());

        process.addFlowElement(createSequenceFlow("start", "task1"));
        process.addFlowElement(createSequenceFlow("task1", "task2"));
        process.addFlowElement(createSequenceFlow("task2", "end"));

        // 2. 流程图自动布局（位于activiti-bpmn-layout模块）
        new BpmnAutoLayout(model).execute();

        // 3. 把BpmnModel对象部署到引擎
        Deployment deploy = factory.buildBpmn("dynamic-model", model, "my-process", true);
        log.debug(deploy.getId() + ", " + deploy.getName());

    }
    // 创建用户任务
    protected UserTask createUserTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }
    // 添加流程顺序
    protected SequenceFlow createSequenceFlow(String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }
    // 创建开始事件
    protected StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }
    // 创建结束事件
    protected EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }
}
