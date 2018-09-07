package api.handle.http.router;

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
        String s = req.body();
        String body = StringUtils.strip(s,"[]");
        JSONObject jsonObject = JSONObject.fromObject(body);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        LOGGER.info("username: {}  password: {}",username,password);
        String token = apiKeyService.getToken(username,password);
        LOGGER.info("token: {}",token);
        String apikey = apiKeyService.getApi_Key(token);
        LOGGER.info("apikey: {}",apikey);
        return "ok";
    }
}
