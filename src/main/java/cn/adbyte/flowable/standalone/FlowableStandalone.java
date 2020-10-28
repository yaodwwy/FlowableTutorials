package cn.adbyte.flowable.standalone;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.impl.history.HistoryLevel;
import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.listener.MyEventListener;
import org.flowable.listener.MyJobEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adam
 */
@Slf4j
public class FlowableStandalone {

    public ProcessEngine processEngine() {

        /**
         * 数据源配置
         */
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=30000")
                .setJdbcUsername("sa")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setAsyncExecutorActivate(true)
                .setHistory(HistoryLevel.AUDIT.getKey())
//                .setCreateDiagramOnDeploy(true)

                /**
                 * Mail配置
                 */
                .setMailServerHost("smtp.163.com")
                .setMailServerPort(25)
                .setMailServerDefaultFrom("abc@163.com")
                .setMailServerUsername("abc@163.com")
                .setMailServerPassword("123456");

        //事件侦听器
        cfg.setEventListeners(getEventListener());
        //类型事件侦听器
        cfg.setTypedEventListeners(getTypeEventListener());

        ProcessEngine processEngine = cfg.buildProcessEngine();
        String pName = processEngine.getName();
        String ver = ProcessEngine.VERSION;
        log.debug("ProcessEngine [" + pName + "] Version: [" + ver + "]");

        return processEngine;
    }

    private Map<String, List<FlowableEventListener>> getTypeEventListener() {
        Map<String, List<FlowableEventListener>> map = new HashMap<>();
        FlowableEventListener myJobEventListener = new MyJobEventListener();
        List<FlowableEventListener> objects = new ArrayList<>();
        objects.add(myJobEventListener);
        map.put("JOB_EXECUTION_SUCCESS,JOB_EXECUTION_FAILURE", objects);
        return map;
    }

    private List<FlowableEventListener> getEventListener() {
        List<FlowableEventListener> list = new ArrayList<>();
        list.add(new MyEventListener());
        return list;
    }

    public RepositoryService repositoryService() {
        // 存储服务
        return processEngine().getRepositoryService();
    }

    public RuntimeService runtimeService() {
        // 运行时服务
        return processEngine().getRuntimeService();
    }

    public TaskService taskService() {
        // 任务服务
        return processEngine().getTaskService();
    }

    public IdentityService identityService() {
        // 用户身份服务
        return processEngine().getIdentityService();
    }

    public ManagementService managementService() {
        // 管理服务
        return processEngine().getManagementService();
    }

    public FormService formService() {
        // 表单服务
        return processEngine().getFormService();
    }

    public HistoryService historyService() {
        // 历史记录
        return processEngine().getHistoryService();
    }
}
