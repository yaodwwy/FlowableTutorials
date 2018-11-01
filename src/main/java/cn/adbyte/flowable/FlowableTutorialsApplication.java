package cn.adbyte.flowable;

import org.flowable.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Adam
 */
@SpringBootApplication
public class FlowableTutorialsApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(FlowableTutorialsApplication.class, args);
    }
}
