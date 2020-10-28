package cn.adbyte.flowable.rest.test;

import lombok.Data;

@Data
public class FeignAuthResult {
    private boolean authenticated;
    private String user;
}
