package standalone;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
//                .setCreateDiagramOnDeploy(true)

                /**
                 * Mail配置
                 */
                .setMailServerHost("smtp.163.com")
                .setMailServerPort(25)
                .setMailServerDefaultFrom("abc@163.com")
                .setMailServerUsername("abc@163.com")
                .setMailServerPassword("123456");

        ProcessEngine processEngine = cfg.buildProcessEngine();
        String pName = processEngine.getName();
        String ver = ProcessEngine.VERSION;
        log.debug("ProcessEngine [" + pName + "] Version: [" + ver + "]");

        return processEngine;
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
