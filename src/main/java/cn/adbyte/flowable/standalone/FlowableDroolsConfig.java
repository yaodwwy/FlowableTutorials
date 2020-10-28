package cn.adbyte.flowable.standalone;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Adam
 */
@Import({FlowableFactory.class})
public class FlowableDroolsConfig {

    public KieServices kieServices() {
        // 通过KieServices对象得到一个KieContainer，利用kieContainer对象创建一个新的KieSession
        // KieServices就是一个中心，通过它来获取的各种对象来完成规则构建、管理和执行等操作
        return KieServices.Factory.get();
    }

    public KieContainer kieContainer() {
        // KieContainer就是一个KieBase的容器
        // KieBase就是一个知识仓库，包含了若干的规则、流程、方法等
        // 创建KieBase是一个成本非常高的事情，KieBase会建立知识（规则、流程）仓库
        return kieServices().getKieClasspathContainer();
    }

    public KieRepository kieRepository() {
        /**
         * KieRepository是一个单例对象，它是一个存放KieModule的仓库
         * KieProject：
         * KieContainer通过KieProject来初始化、构造KieModule，
         * 并将KieModule存放到KieRepository中，然后KieContainer可以通过KieProject来查找KieModule定义的信息，
         * 并根据这些信息构造KieBase和KieSession。
         * */
       return kieServices().getRepository();
    }

    public ProcessEngine processEngine() {

        StandaloneProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration();
        cfg.setJdbcUrl("jdbc:mysql://localhost:3306/ACT?characterEncoding=UTF-8")
                .setJdbcUsername("root")
                .setJdbcPassword("123456")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setAsyncExecutorActivate(true)
                .setCreateDiagramOnDeploy(true);

        ProcessEngine processEngine = cfg.buildProcessEngine();
        String pName = processEngine.getName();
        String ver = ProcessEngine.VERSION;
        System.out.println("ProcessEngine [" + pName + "] Version: [" + ver + "]");

        return processEngine;
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

}
