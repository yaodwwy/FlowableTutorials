package standalone.feign.api;

import com.google.gson.JsonObject;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import standalone.feign.api.entity.TaskEntityCustom;

import java.util.Map;

/**
 * REST 任务服务
 *
 * @author Adam
 * @see <a href='https://www.activiti.org/userguide/#_tasks'></a>
 */
public interface TaskApi {

    @RequestLine("GET runtime/tasks")
    public Results<TaskEntityCustom> listTasks(@QueryMap Map<String, Object> map);

    @RequestLine("POST runtime/tasks/{taskId}")
    @Headers("Content-Type: application/json")
    public Integer taskActions(@Param("taskId") String taskId, JsonObject params);
}
