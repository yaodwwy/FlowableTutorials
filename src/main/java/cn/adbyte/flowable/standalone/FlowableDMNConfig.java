package cn.adbyte.flowable.standalone;

import org.flowable.dmn.api.DmnRepositoryService;
import org.flowable.dmn.engine.DmnEngine;
import org.flowable.dmn.engine.DmnEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Adam
 */
public class FlowableDMNConfig {


    public DmnEngine dmnEngine() {
        /**
         * 数据源配置
         */
        DmnEngineConfiguration config = DmnEngineConfiguration.createStandaloneDmnEngineConfiguration();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/ACT?characterEncoding=UTF-8");
        config.setJdbcDriver("com.mysql.jdbc.Driver");
        config.setJdbcUsername("root");
        config.setJdbcPassword("123456");
        config.setDatabaseSchemaUpdate("true");
        //MVEL方法注册器
        return config.buildDmnEngine();
    }

    public DmnRepositoryService dmnRepositoryService() {
        // 存储服务
        return dmnEngine().getDmnRepositoryService();
    }
}
