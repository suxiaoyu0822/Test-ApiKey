package api.handle.http.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-10 下午4:47
 */

public class SelectAuthenticationMethodRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectAuthenticationMethodRoute.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOGGER.info("Choose authentication mode API KEY or JWT");
        //发布api时，选择认证方式。API KEY or JWT
        return "OK";
    }
}
