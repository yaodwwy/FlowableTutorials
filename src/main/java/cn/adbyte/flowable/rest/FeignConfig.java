package cn.adbyte.flowable.rest;

import cn.adbyte.flowable.rest.decoder.BytesDecoder;
import feign.Feign;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Adam
 */
@Slf4j
@Configuration
public class FeignConfig {

    private final BasicAuthRequestInterceptor AUTH = new BasicAuthRequestInterceptor("", "");
    private final Logger.JavaLogger LOGGER = new Logger.JavaLogger().appendToFile("target/http.log");

    @Bean
    public Feign.Builder builder() {
        Feign.Builder builder = Feign.builder()
                .client(new OkHttpClient())
                .logger(LOGGER).logLevel(Logger.Level.BASIC)
                .requestInterceptor(AUTH);
        log.info("builder : {}", builder);
        return builder;
    }

}
