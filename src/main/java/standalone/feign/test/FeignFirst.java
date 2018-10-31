package standalone.feign.test;

import feign.RequestLine;

public interface FeignFirst {

    @RequestLine("GET /ip")
    Ip getIp();
}
