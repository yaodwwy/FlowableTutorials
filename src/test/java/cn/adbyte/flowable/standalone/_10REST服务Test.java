package cn.adbyte.flowable.standalone;

import cn.adbyte.flowable.rest.test.FeignAuth;
import cn.adbyte.flowable.rest.test.FeignFirst;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import org.junit.Test;

/**
 * 更多测试参考
 * the {@link Out} test class.
 */
public class _10REST服务Test {

    @Test
    public void Feign() {
        // 获取服务接口
        FeignFirst client = Feign.builder()
                .decoder(new GsonDecoder())
                .target(FeignFirst.class, "https://httpbin.org/");
        // 请求接口
        System.out.println(client.getIp());
    }

    @Test
    public void feignBasicAuth() {
        // 获取服务接口
        FeignAuth client = Feign.builder()
                .decoder(new GsonDecoder())
                .requestInterceptor(new BasicAuthRequestInterceptor("user", "passwd"))
                .target(FeignAuth.class, "https://httpbin.org/");
        // 请求接口
        System.out.println(client.getAuthResult());
    }

}
