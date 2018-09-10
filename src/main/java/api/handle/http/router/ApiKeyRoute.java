package api.handle.http.router;

import api.handle.dto.ApiKey;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.UserEntity;
import api.handle.http.json.DefaultGsonJson;
import api.handle.jwt.AboutJWT;
import api.handle.service.ApiKeyService;
import com.google.inject.Inject;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;


/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-8-24 下午3:15
 */
public class ApiKeyRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiKeyRoute.class);
    private ApiKeyService apiKeyService;
    @Inject
    public ApiKeyRoute(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }
    @Override
    public Object handle(Request req, Response resp) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        String ApiKey = bodyJsonEntity.getQueryParameter(req,"API_KEY",false);
        LOGGER.info("somthing: {}",ApiKey);
        String token = apiKeyService.getToken(ApiKey);
        LOGGER.info("token: {}",token);
        String Api_Key=apiKeyService.getApi_Key(token);
        LOGGER.info("Api_Key: {}",Api_Key);
        return "1212";
    }

}
