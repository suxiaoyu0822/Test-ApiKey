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
 * @Date: Created in 18-10-9 上午9:33
 */

public class ManageDeletUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageDeletUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageDeletUserRoute(Ldap ldap){
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
        ldap.connect();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        //判断是否为引用源,否-直接删除 是-无法删除，请先解除其他uid=dn的引用，再进行删除
        dn = "ou="+oldorganizationalUnit+",o="+oldorganization+dn;
        String description = ldap.searchDescription(username,dn);
        if (description.equals("BeQuote")){
            return "删除失败，请先解除其他uid=dn的引用，再进行删除";
        }
        System.out.println("删除用户");
        ldap.delete("cn="+username+",ou="+oldorganizationalUnit+",o="+oldorganization+dn);
        System.out.println("[成功删除用户]");
        ldap.close();
        return "成功删除用户";

    }
}
