package api.handle.http.router;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.authority.resourcesldap.impl.ResourceLdapImpl;
import api.handle.http.router.aboutJWT.*;
import api.handle.http.router.authorityRouter.*;
import api.handle.http.router.manage.*;
import api.handle.jwt.jwtldap.JwtLdap;
import api.handle.jwt.jwtldap.impl.JwtLdapImpl;
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
            post("/AddO",new ManageAddOrganizationRoute(ldap));
            post("/DeletO",new ManageDeletOrganizationRoute(ldap));
            post("/UpdataO",new ManageUpdataOrganizationRoute(ldap));
            post("/SearchO",new ManageSearchOrganizationRoute(ldap));
            post("/AddOU",new ManageAddOrganizationalUnitRoute(ldap));
            post("/DeletOU",new ManageDeletOrganizationalUnitRoute(ldap));
            post("/UpdataOU",new ManageUpdataOrganizationalUnitRoute(ldap));
            post("/SearchOU",new ManageSearchOrganizationalUnitRoute(ldap));
            post("/AddUser",new ManageAddUserRoute(ldap));
            post("/DeletUser",new ManageDeletUserRoute(ldap));
            post("/UpdataUser",new ManageUpdataUserRoute(ldap));
            post("/SearchUser",new ManageSearchUserRoute(ldap));
            post("/MoveUser",new ManageMoveUserRoute(ldap));
            post("/QuoteUser",new ManageQuoteUserRoute(ldap));
            post("/SearchAll",new ManageSearchAllRoute(ldap));
            post("/MoveUpdata",new ManageMoveUpdataUserRoute(ldap));
        });
        path("/authority", () -> {
            ResourceLdap resourceLdap = new ResourceLdapImpl();
            post("/AddResourcesOU",new AuthorityAddResourcesOURoute(resourceLdap));
            post("/CoverResourcesOU",new AuthorityCoverResourcesOURoute(resourceLdap));
            post("/AddSecurityOU",new AuthorityAddSecurityOURoute(resourceLdap));
            post("/AddRule",new AuthorityAddRuleRoute(resourceLdap));
            post("/DeletRule",new AuthorityDeletRuleRoute(resourceLdap));
            post("/UpdateRule",new AuthorityUpdateRuleRoute(resourceLdap));
            post("/SearchRule",new AuthoritySearchRuleRoute(resourceLdap));
            post("/MatchProcess",new AuthorityProcessMatchRuleRoute(resourceLdap));
            post("/SearchAllOrgnization",new AuthoritySearchAllOrgnizationRoute(resourceLdap));
            post("/SearchAllSecurity",new AuthoritySearchAllSecurityRoute(resourceLdap));
            post("/InverseQuery",new AuthorityInverseQueryRoute(resourceLdap));
            post("/DeletNode",new AuthorityDeletNodeRoute(resourceLdap));
            post("/UpdateNode",new AuthorityUpdateNodeRoute(resourceLdap));
            post("/SearchResourcesForSecurity",new AuthoritySearchResourcesForSecurityRoute(resourceLdap));
        });
        path("/jwt", () -> {
            JwtLdap jwtLdap = new JwtLdapImpl();
            post("/AddJwt",new AddJWTRoute(jwtLdap));
            post("/DeleteJwt",new DeleteJWTRoute(jwtLdap));
            post("/SearchToken",new SearchTokenRoute(jwtLdap));
            post("/ResetJWT",new ResetJWTRoute(jwtLdap));
            post("/Authentication",new AuthenticationRoute(jwtLdap));
        });
    }
}
