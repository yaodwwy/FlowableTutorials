package standalone.feign.decoder;

import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Adam
 */
public class BytesDecoder implements Decoder {
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        return response.body().asInputStream();
        //return response.body().asInputStream().readAllBytes();
    }
}
