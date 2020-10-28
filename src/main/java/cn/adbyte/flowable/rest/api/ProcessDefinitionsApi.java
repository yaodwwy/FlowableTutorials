package cn.adbyte.flowable.rest.api;

import feign.QueryMap;
import feign.RequestLine;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.rest.service.api.repository.ProcessDefinitionResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * REST 流程定义服务
 *
 * @author Adam
 * @see <a href='https://www.activiti.org/userguide/#_process_definitions'></a>
 */
public interface ProcessDefinitionsApi {

    @RequestLine("GET repository/process-definitions")
    DataResponse<ProcessDefinitionResponse> getProcessDefinitions(@QueryMap Map<String, String> allRequestParams);

    @RequestLine("GET repository/process-definitions/{processDefinitionId}")
    ProcessDefinitionResponse getProcessDefinition(@PathVariable String processDefinitionId);

//    @RequestLine("PUT repository/process-definitions/{processDefinitionId}")
//    ProcessDefinitionResponse executeProcessDefinitionAction(
//            @PathVariable String processDefinitionId,
//            @RequestBody ProcessDefinitionActionRequest actionRequest);
}
