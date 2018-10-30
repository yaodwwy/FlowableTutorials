package standalone;

import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 工作的产生与管理
 * 异步任务
 * 定时事件
 * 暂停的工作
 * 无法执行的工作
 */
@RunWith(SpringRunner.class)
@Import({FlowableStandaloneConfig.class})
public class _4流程操作Test {

    @Autowired
    private FlowableFactory factory;

    @Test
    public void 暂停挂起流程定义(){
        ProcessInstance processInstance = factory.deployAndStart("processes/_1first.bpmn20.xml");
        String processDefinitionID = processInstance.getProcessDefinitionId();
        factory.suspendProcessDefinitionById(processDefinitionID);
        try {
            factory.startProcessInstanceById(processDefinitionID);
        } catch (FlowableException e) {
            String message = e.getMessage();
            System.out.println(message);
            boolean suspended = message.endsWith("suspended");
            Assert.assertTrue(suspended);
        }
    }

    @Test
    public void 捕获信号事件触发流程继续() {
        ProcessInstance processInstance = factory.deployAndStart("processes/_4捕获信号事件触发流程继续.bpmn20.xml");

        // 查当前的子执行流（只有一个）
        List<Execution> exe = factory.listChildExecution(processInstance.getId());
        System.out.println("当前节点：");
        Print.exec(exe);

        factory.signalEventReceived("testSignal");

        //再查一次
        exe = factory.listChildExecution(processInstance.getId());

        System.out.println("当前节点：");
        Print.exec(exe);
    }

    @Test
    public void 捕获消息事件触发流程继续() {
        ProcessInstance processInstance = factory.deployAndStart("processes/_4捕获消息事件触发流程继续.bpmn20.xml");
//        MessageEvent.bpmn
        // 查当前的子执行流（只有一个）
        List<Execution> exe = factory.listChildExecution(processInstance.getId());
        System.out.println("当前节点：");
        Print.exec(exe);

        // 一个消息触发的中间捕获事件
        // 让它往前走
        factory.messageEventReceived("testMsg", exe.get(0).getId());

        exe = factory.listChildExecution(processInstance.getId());
        System.out.println("当前节点：");
        Print.exec(exe);
    }

    @Test
    public void 接收任务触发流程继续() {
        ProcessInstance processInstance = factory.deployAndStart("processes/_4接收任务触发流程继续.bpmn20.xml");
        // 查当前的子执行流（只有一个）
        List<Execution> exe = factory.listChildExecution(processInstance.getId());
        System.out.println("当前节点:");
        Print.exec(exe);

        // 等待任务，也就是说需要手动推进下一步的执行
        // 让它往前走
        factory.trigger(exe.get(0).getId());

        exe = factory.listChildExecution(processInstance.getId());
        System.out.println("当前节点:");
        Print.exec(exe);
    }

    @Test
    public void 流程操作() throws InterruptedException {
        ProcessInstance processInstance = factory.deployAndStart("processes/_4流程操作.bpmn20.xml");
        String processInstanceID = processInstance.getId();
        Print.instances(processInstance);
        System.out.println("==================等20秒以上再关！等下会有异常处理类打印==================");

        /*
        ISO_8601格式：（P ,Y,M,W,D,T,.H,M,S）
        当一个流程被挂起后,是不能继续新建立这个流程的实例了,会有异常抛出,请注意在上面的方法中,
        可以设定这个流程实例的过期时间,也可以通过流程实例id去挂起激活流程:
         */
        // 中止
        factory.suspendProcessInstanceById(processInstanceID);
        Thread.sleep(19_000);
        // 再激活
        factory.activateProcessInstanceById(processInstanceID);
    }

}
