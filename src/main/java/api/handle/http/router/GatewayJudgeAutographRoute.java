package api.handle.http.router;

import api.handle.dto.ApiKey;
import api.handle.dto.BodyJsonEntity;
import api.handle.jwt.AboutJWT;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-10 下午4:57
 */

public class GatewayJudgeAutographRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayJudgeAutographRoute.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        String ApiKey = bodyJsonEntity.getQueryParameter(request,"API_KEY",false);
        LOGGER.info("ApiKey {}",ApiKey);
        //验证签名并返回结果
        String token = StringUtils.substringBeforeLast(ApiKey, "~");
        LOGGER.info("token {}",token);
        String path = StringUtils.substringAfterLast(ApiKey, "~");
        LOGGER.info("path {}",path);
        AboutJWT aboutJWT = new AboutJWT();
        api.handle.dto.ApiKey apiKey = new ApiKey();
        apiKey.setSecret("This is a mark.");
        aboutJWT.parseJWT(token,apiKey);
        return "OK";
    }
}
