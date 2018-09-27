package api.handle.http.router;

import api.handle.http.router.manage.ManageOrganizationRoute;
import api.handle.http.router.manage.ManageOrganizationalUnitRoute;
import api.handle.http.router.manage.ManageUserRoute;
import api.handle.ldap.impl.LdapImpl;
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
            LdapImpl ldap = new LdapImpl();
            post("/BasicRegister",new BasicUserRegisterRoute());
            post("/Digest",new DigestUserRegisterRoute());
            post("/Register",new UserRegisterRoute(ldap));
        });
        path("/gateway", () -> {
            get("/judge",new GatewayJudgeAutographRoute());
        });
        path("/manage", () -> {
            LdapImpl ldap = new LdapImpl();
            post("/organization",new ManageOrganizationRoute(ldap));
            post("/organizationalUnit",new ManageOrganizationalUnitRoute(ldap));
            post("/user",new ManageUserRoute(ldap));
        });
    }
}
