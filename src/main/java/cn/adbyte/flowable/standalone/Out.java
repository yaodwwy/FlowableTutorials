package cn.adbyte.flowable.standalone;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 输出对象到json
 */
@Slf4j
public class Out {


    @SneakyThrows
    public static void print(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(objectMapper.writeValueAsString(obj));
    }
}