package cn.adbyte.flowable.rest.api;

import com.google.gson.JsonObject;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import io.swagger.annotations.ApiParam;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.rest.service.api.runtime.task.TaskActionRequest;
import org.flowable.rest.service.api.runtime.task.TaskResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * REST 任务服务
 *
 * @author Adam
 * @see <a href='https://www.activiti.org/userguide/#_tasks'></a>
 */
public interface TaskApi {

    @RequestLine("GET runtime/tasks")
    DataResponse<TaskResponse> getTasks(@QueryMap Map<String, String> requestParams);

    @RequestLine("POST runtime/tasks/{taskId}")
    @Headers("Content-Type: application/json")
    void executeTaskAction(@PathVariable String taskId, @RequestBody TaskActionRequest actionRequest);
}
