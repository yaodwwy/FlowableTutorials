package cn.adbyte.flowable.rest.test;

import feign.RequestLine;

public interface FeignFirst {

    @RequestLine("GET /ip")
    Ip getIp();
}
