package cn.adbyte.flowable.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.flowable.app.spring.SpringAppEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    final private Logger logger = LoggerFactory.getLogger(FlowableConfig.class);

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid.main")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.flowable")
    public DataSource dataSourceFlowable() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        log.debug("DruidDataSource [" + dataSource.getUrl() + "] login by: [" + dataSource.getUsername() + "]");
        return dataSource;
    }

    @Override
    public void configure(SpringAppEngineConfiguration engineConfiguration) {
        engineConfiguration.setDataSource(dataSourceFlowable());
    }
}
