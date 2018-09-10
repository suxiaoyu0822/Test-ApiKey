package api.handle.http.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-10 下午4:37
 */

public class GetCertificateRout implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetCertificateRout.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOGGER.info("Successful application certificate.");
        //成功生成应用证书
        return "OK";
    }
}
