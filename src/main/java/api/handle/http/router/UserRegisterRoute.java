package api.handle.http.router;

import api.handle.dto.ApiKey;
import api.handle.dto.BodyJsonEntity;
import api.handle.jwt.AboutJWT;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-10 下午4:52
 */

public class UserRegisterRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisterRoute.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //用户注册，生成并返回API_KEY
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        String jwt =bodyJsonEntity.getHeaders(request,"Authorization",false);
        LOGGER.info("Authorization: {}",jwt);
        //验证用户名和密码
        String info = new String(Base64.decodeBase64(jwt.getBytes()));
        LOGGER.info("info: {}",info);

        AboutJWT aboutJWT = new AboutJWT();
        ApiKey apiKey = new ApiKey();
        String id = "1";
        String issuer= "sxy";
        String subject= "Test somthing";
        long ttlMillis=100000;
        apiKey.setSecret("This is a mark.");
        String identity_token =aboutJWT.createJWT(id,issuer,subject,ttlMillis,apiKey);
        LOGGER.info("identity Token: {}",identity_token);

        return identity_token;
    }
}
