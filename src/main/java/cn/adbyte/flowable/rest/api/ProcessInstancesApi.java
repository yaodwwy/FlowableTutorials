package cn.adbyte.flowable.rest.api;

import feign.Headers;
import feign.RequestLine;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * REST 流程实例服务
 *
 * @author Adam
 * @see <a href='https://www.activiti.org/userguide/#_process_instances'></a>
 */
public interface ProcessInstancesApi {

    @Headers("Content-Type: application/json")
    @RequestLine("POST runtime/process-instances")
    ProcessInstanceResponse createProcessInstance(@RequestBody ProcessInstanceCreateRequest request);

    /**
     * 更多内容不想再继续调用下去了，
     * 太多了。。。看API吧！
     * 以常用的添加到Feign服务里可以日常测试即可
     */
}
