package api.handle.http.router;

import api.handle.dto.ApiKey;
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
    public String handle(Request req, Response resp) throws Exception {
        String s = req.body();
        String body = StringUtils.strip(s,"[]");
        JSONObject jsonObject = JSONObject.fromObject(body);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        LOGGER.info("username: {}  password: {}",username,password);

        String Basics_token = apiKeyService.getToken(username,password);
        LOGGER.info("Basics token: {}",Basics_token);

        String random_key = apiKeyService.getApi_Key(Basics_token);
        LOGGER.info("random key: {}",random_key);

        AboutJWT aboutJWT = new AboutJWT();
        ApiKey apiKey = new ApiKey();
        String id = "1";
        String issuer= username;
        String subject= "Test somthing";
        long ttlMillis=10000;
        apiKey.setSecret(random_key);
        String identity_token =aboutJWT.createJWT(id,issuer,subject,ttlMillis,apiKey);
        LOGGER.info("identity Token: {}",identity_token);
        //解析
        aboutJWT.parseJWT(identity_token,apiKey);
        return identity_token;
    }
}
