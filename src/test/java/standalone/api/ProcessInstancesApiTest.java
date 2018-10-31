package standalone.api;

import com.google.gson.Gson;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import standalone.feign.FeignConfig;
import standalone.feign.api.ProcessInstancesApi;
import standalone.feign.api.entity.ProcessInstanceEntityCustom;
import standalone.feign.api.param.StartProcessInstanceParam;
import standalone.feign.decoder.StatusDecoder;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@Import({FeignConfig.class})
public class ProcessInstancesApiTest {

    private final String url = "http://localhost:8080/activiti-rest/service";

    @Autowired
    private Feign.Builder builder;

    private ProcessInstancesApi processInstances;
    private ProcessInstancesApi statusProcessInstances;

    @PostConstruct
    private void init(){
        GsonDecoder decoder = new GsonDecoder();
        processInstances = builder.decoder(decoder).target(ProcessInstancesApi.class, url);
        StatusDecoder statusDecoder = new StatusDecoder();
        statusProcessInstances = builder.decoder(statusDecoder).target(ProcessInstancesApi.class, url);
    }

    @Test
    public void startProcessInstance() {
        StartProcessInstanceParam.ById id = StartProcessInstanceParam.createId("审核退回:3:515068", null, null);
//        StartProcessInstanceParam.ByKey key = StartProcessInstanceParam.createKey("审核退回",null,null,null);
//        StartProcessInstanceParam.ByMessage message = StartProcessInstanceParam.createMessage("start",null,null,null);

        ProcessInstanceEntityCustom instance = processInstances.startProcessInstance(new Gson().toJson(id));
//        ProcessInstanceEntityCustom instance1 = processInstances.startProcessInstance(new Gson().toJson(key));
//        ProcessInstanceEntityCustom instance2 = processInstances.startProcessInstance(new Gson().toJson(message));
        System.out.println(instance);
//        System.out.println(instance1);
//        System.out.println(instance2);
    }
}