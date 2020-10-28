package cn.adbyte.flowable.rest.api;

import cn.adbyte.flowable.rest.FeignConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.gson.GsonDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.rest.service.api.repository.ProcessDefinitionResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@Import({FeignConfig.class})
public class ProcessDefinitionsApiTest {

    private final String url = "http://localhost:8080/process-api/";

    @Autowired
    private Feign.Builder builder;

    private ProcessDefinitionsApi processDefinitionsApi;

    @Before
    public void init() {
        log.info("====== builder: {}",builder);
        processDefinitionsApi = builder
                .decoder(new GsonDecoder())
                .target(ProcessDefinitionsApi.class, url);
    }

    @Test
    public void listProcessDefinitions() {
        Map<String, String> map = new HashMap<>();
//        map.put("key", "审核退回");
//        map.put("deploymentId", "515018");
//        map.put("category", "_");
        DataResponse<ProcessDefinitionResponse> definitions = processDefinitionsApi.getProcessDefinitions(map);
        print(definitions);
    }

    @Test
    public void getProcessDefinition() {
        ProcessDefinitionResponse pd = processDefinitionsApi.getProcessDefinition("myProcess:1:2504");
        print(pd);
    }

    @SneakyThrows
    static void print(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(objectMapper.writeValueAsString(obj));
    }
}