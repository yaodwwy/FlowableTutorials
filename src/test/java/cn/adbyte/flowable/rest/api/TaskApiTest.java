package cn.adbyte.flowable.rest.api;

import cn.adbyte.flowable.rest.FeignConfig;
import cn.adbyte.flowable.rest.decoder.StatusDecoder;
import cn.adbyte.flowable.standalone.Out;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.assertj.core.util.Lists;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.rest.service.api.engine.variable.RestVariable;
import org.flowable.rest.service.api.runtime.task.TaskActionRequest;
import org.flowable.rest.service.api.runtime.task.TaskResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@Import({FeignConfig.class})
public class TaskApiTest {

    private final String url = "http://localhost:8080/activiti-rest/service";

    @Autowired
    private Feign.Builder builder;

    private TaskApi taskApi;
    private TaskApi statusTaskApi;

    @PostConstruct
    private void init() {
        taskApi = builder.decoder(new GsonDecoder()).target(TaskApi.class, url);
        statusTaskApi = builder.encoder(new GsonEncoder()).decoder(new StatusDecoder()).target(TaskApi.class, url);
    }

    @Test
    public void listTasks() {
        Map<String, String> map = new HashMap<>();
        map.put("processInstanceId", "515069");
        DataResponse<TaskResponse> tasks = taskApi.getTasks(map);
        Out.print(tasks);
    }

    @Test
    public void taskActions() {
        TaskActionRequest param = new TaskActionRequest();
        RestVariable restVariable = new RestVariable();
        restVariable.setName("var");
        restVariable.setValue("0");
        param.setAction(TaskActionRequest.ACTION_COMPLETE);
        param.setVariables(Lists.list(restVariable));
        statusTaskApi.executeTaskAction("515078", param);

    }
}
