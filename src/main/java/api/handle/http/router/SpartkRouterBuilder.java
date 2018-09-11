package api.handle.http.router;

import api.handle.service.impl.ApiKeyServiceImpl;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

/**
 *
 * @outhor 苏晓
 * @create 2018-02-26-10:06
 */
public class SpartkRouterBuilder extends AbstractSpartkRouterBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpartkRouterBuilder.class);
    @Inject
    public SpartkRouterBuilder() {

    }

    @Override
    void buildApi() {
        get("/hello", (r, a) -> "hello");
        buildMessageCollectionApi();
    }
    private void buildMessageCollectionApi() {
        path("/key", () -> {
            ApiKeyServiceImpl apiKeyService = new ApiKeyServiceImpl();
            get("/do",new ApiKeyRoute(apiKeyService));
        });
        path("/application", () -> {
            get("/Certificate",new GetCertificateRout());
            post("/Authentication_method",new SelectAuthenticationMethodRoute());
        });
        path("/user", () -> {
            post("/Register",new UserRegisterRoute());
            post("/Digest",new DigestUserRegisterRoute());
        });
        path("/gateway", () -> {
            get("/judge",new GatewayJudgeAutographRoute());
        });
    }
}
