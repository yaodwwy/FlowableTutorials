package standalone.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import standalone.feign.FeignConfig;
import standalone.feign.api.Results;
import standalone.feign.api.TaskApi;
import standalone.feign.api.entity.TaskEntityCustom;
import standalone.feign.api.param.TaskActionParam;
import standalone.feign.api.param.VariableParam;
import standalone.feign.decoder.StatusDecoder;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> map = new HashMap<>();
        map.put("processInstanceId", "515069");
        Results<TaskEntityCustom> results = taskApi.listTasks(map);
        System.out.println(results);
    }

    @Test
    public void taskActions() {
        Gson gson = new Gson();
        VariableParam variableParam = new VariableParam();
        variableParam.setName("var");
        variableParam.setValue("0");
        TaskActionParam.Complete complete = TaskActionParam.createComplete(TaskActionParam.ActionType.complete, List.of(variableParam));
        JsonObject object = gson.toJsonTree(complete).getAsJsonObject();
        Integer integer = statusTaskApi.taskActions("515078", object);
        System.out.println(integer);
    }
}