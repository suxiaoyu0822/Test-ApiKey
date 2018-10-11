package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.OrganizationEntity;
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
 * @Date: Created in 18-10-9 上午9:32
 */

public class ManageUpdataOrganizationRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageUpdataOrganizationRoute.class);
    private Ldap ldap;
    @Inject
    public ManageUpdataOrganizationRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        OrganizationEntity organizationEntity= bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        String oldorganization = organizationEntity.getOldorganization();
        String neworganization = organizationEntity.getNeworganization();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        ldap.connect();
        //修改组织
        System.out.println("修改组织");
        if (!ldap.isExistInLDAP("o="+oldorganization+dn)){
            System.out.println("[所修改组织不存在]");
            return "所修改组织不存在";
        }
        dn ="o="+oldorganization+dn;
        String newo= "o="+neworganization;
        ldap.updateNodes(dn,newo);
        ldap.close();
        System.out.println("[成功修改组织]");
        return "成功修改组织";
    }
}
