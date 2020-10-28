package cn.adbyte.flowable.rest.api;

import cn.adbyte.flowable.rest.FeignConfig;
import cn.adbyte.flowable.rest.decoder.StatusDecoder;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static cn.adbyte.flowable.rest.api.ProcessDefinitionsApiTest.print;

@RunWith(SpringRunner.class)
@Import({FeignConfig.class})
public class ProcessInstancesApiTest {

    private final String url = "http://localhost:8080/activiti-rest/service";

    @Autowired
    private Feign.Builder builder;

    private ProcessInstancesApi processInstances;

    @PostConstruct
    private void init(){
        GsonDecoder decoder = new GsonDecoder();
        processInstances = builder.decoder(decoder).target(ProcessInstancesApi.class, url);
    }

    @Test
    public void startProcessInstance() {
        ProcessInstanceCreateRequest param = new ProcessInstanceCreateRequest();
        param.setProcessDefinitionId("审核退回:3:515068");
        ProcessInstanceResponse processInstance = processInstances.createProcessInstance(param);
        print(processInstance);
    }
}