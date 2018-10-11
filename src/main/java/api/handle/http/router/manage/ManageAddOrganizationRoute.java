package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.OrganizationEntity;
import api.handle.ldap.Ldap;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-9 上午9:31
 */

public class ManageAddOrganizationRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageAddOrganizationRoute.class);
    private Ldap ldap;
    @Inject
    public ManageAddOrganizationRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        OrganizationEntity organizationEntity= bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        String oldorganization = organizationEntity.getOldorganization();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        ldap.connect();
        //创建创建组织
        System.out.println("创建组织");
        if (ldap.isExistInLDAP("o="+oldorganization+dn)){
            System.out.println("[所创建组织已存在]");
            return "所创建组织已存在";
        }
        ldap.addO(oldorganization);
        ldap.close();
        System.out.println("[成功创建组织]");
        return "成功创建组织";
    }
}
