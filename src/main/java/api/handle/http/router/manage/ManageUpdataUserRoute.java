package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.OrganizationEntity;
import api.handle.dto.UserEntity;
import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-9 上午9:34
 */

public class ManageUpdataUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageUpdataUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageUpdataUserRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        OrganizationEntity organizationEntity = bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        String oldorganization = organizationEntity.getOldorganization();
        String oldorganizationalUnit = organizationEntity.getOldorganizationalUnit();
        String username = userEntity.getUsername();
        String keyword=userEntity.getKeyword();
        String newinfo = userEntity.getNewinfo();
        ldap.connect();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        System.out.println("修改用户信息");
        dn = "cn="+username+",ou="+oldorganizationalUnit+",o="+oldorganization+dn;
        ldap.update(keyword,newinfo,dn);
        System.out.println("[修改用户信息成功]");
        return "修改用户信息成功";
    }
}
