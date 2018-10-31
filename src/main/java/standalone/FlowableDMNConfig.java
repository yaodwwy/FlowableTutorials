package standalone;

import org.flowable.dmn.api.DmnRepositoryService;
import org.flowable.dmn.api.DmnRuleService;
import org.flowable.dmn.engine.DmnEngine;
import org.flowable.dmn.engine.DmnEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Adam
 */
@Configuration
@ComponentScan
public class FlowableDMNConfig {


    @Bean
    public DmnEngine dmnEngine() {
        /**
         * 数据源配置
         */
        DmnEngineConfiguration config = DmnEngineConfiguration.createStandaloneDmnEngineConfiguration();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/ACT?characterEncoding=UTF-8");
        config.setJdbcDriver("com.mysql.jdbc.Driver");
        config.setJdbcUsername("root");
        config.setJdbcPassword("root");
        config.setDatabaseSchemaUpdate("true");
        //MVEL方法注册器
        return config.buildDmnEngine();
    }

    @Bean
    public DmnRepositoryService dmnRepositoryService() {
        // 存储服务
        return dmnEngine().getDmnRepositoryService();
    }

    @Bean
    public DmnRuleService dmnRuleService() {
        // 获取规则服务组件
        return dmnEngine().getDmnRuleService();
    }


}
