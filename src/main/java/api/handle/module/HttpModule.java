package api.handle.module;

import api.handle.http.HttpServer;
import api.handle.http.SparkHttpServer;
import api.handle.http.router.RouterBuilder;
import api.handle.http.router.SpartkRouterBuilder;
import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;
import api.handle.service.ApiKeyService;
import api.handle.service.impl.ApiKeyServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletScopes;

/**
 *
 * @outhor 苏晓
 * @create 2018-01-22-15:11
 */

public class HttpModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Ldap.class).to(LdapImpl.class).in(ServletScopes.REQUEST);
        bind(ApiKeyService.class).to(ApiKeyServiceImpl.class).in(ServletScopes.REQUEST);
        bind(RouterBuilder.class).to(SpartkRouterBuilder.class);
        bind(HttpServer.class).to(SparkHttpServer.class);
    }
}

