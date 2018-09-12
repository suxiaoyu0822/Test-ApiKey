package api.handle.http.router;

import api.handle.dto.ApiKey;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.JWTPayloadEntity;
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

public class BasicUserRegisterRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicUserRegisterRoute.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //基本认证
        //用户注册，生成并返回API_KEY
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        String jwt =bodyJsonEntity.getHeaders(request,"Authorization",false);
        JWTPayloadEntity jwtPayloadEntity = bodyJsonEntity.getBodyJsonEntity(JWTPayloadEntity.class,request);
        String id = jwtPayloadEntity.getId();
        String issuer= jwtPayloadEntity.getIssuer();
        String subject= jwtPayloadEntity.getSubject();
        long ttlMillis=jwtPayloadEntity.getTtlMillis();
        LOGGER.info("Authorization: {}",jwt);
        LOGGER.info("id: {} issuer: {} subject: {} ttlMillis: {}",id,issuer,subject,ttlMillis);
        //验证用户名和密码
        String info = new String(Base64.decodeBase64(jwt.getBytes()));
        LOGGER.info("info: {}",info);

        AboutJWT aboutJWT = new AboutJWT();
        ApiKey apiKey = new ApiKey();
        apiKey.setSecret("This is a mark.");
        String identity_token =aboutJWT.createJWT(id,issuer,subject,ttlMillis,apiKey);
        LOGGER.info("identity Token: {}",identity_token);

        return identity_token;
    }
}
