package cn.adbyte.flowable.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.flowable.app.spring.SpringAppEngineConfiguration;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author Adam
 */
@Slf4j
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringAppEngineConfiguration> {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid.main")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.flowable")
    public DataSource dataSourceFlowable() {
        return DruidDataSourceBuilder.create().build();
    }

    @Override
    public void configure(SpringAppEngineConfiguration cfg) {
        cfg.setDataSource(dataSourceFlowable());
        cfg.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    }
}
