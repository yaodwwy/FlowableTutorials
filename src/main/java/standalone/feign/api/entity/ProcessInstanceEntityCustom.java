package standalone.feign.api.entity;

import lombok.Data;
import standalone.feign.api.param.VariableParam;

import java.util.List;

/**
 * @author Adam
 */
@Data
public class ProcessInstanceEntityCustom {

    private String id;
    private String url;
    private String businessKey;
    private Boolean suspended;
    private Boolean ended;
    private String processDefinitionId;
    private String processDefinitionUrl;
    private String processDefinitionKey;
    private String activityId;
    private List<VariableParam> variables;
    private String tenantId;
    private String name;
    private Boolean completed;
}
