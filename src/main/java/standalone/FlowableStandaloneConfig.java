package standalone;

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

import java.util.List;
import java.util.Map;

/**
 * @author Adam
 */
@Slf4j
@Configuration
@ComponentScan
public class FlowableStandaloneConfig {

    @Bean
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
        return Map.of("JOB_EXECUTION_SUCCESS,JOB_EXECUTION_FAILURE", List.of(new MyJobEventListener()));
    }

    private List<FlowableEventListener> getEventListener() {
        return List.of(new MyEventListener());
    }

    @Bean
    public RepositoryService repositoryService() {
        // 存储服务
        return processEngine().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService() {
        // 运行时服务
        return processEngine().getRuntimeService();
    }

    @Bean
    public TaskService taskService() {
        // 任务服务
        return processEngine().getTaskService();
    }

    @Bean
    public IdentityService identityService() {
        // 用户身份服务
        return processEngine().getIdentityService();
    }

    @Bean
    public ManagementService managementService() {
        // 管理服务
        return processEngine().getManagementService();
    }

    @Bean
    public FormService formService() {
        // 表单服务
        return processEngine().getFormService();
    }

    @Bean
    public HistoryService historyService() {
        // 历史记录
        return processEngine().getHistoryService();
    }
}
