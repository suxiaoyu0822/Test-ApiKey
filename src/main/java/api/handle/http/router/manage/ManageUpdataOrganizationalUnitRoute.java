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
 * @Date: Created in 18-10-9 上午9:33
 */

public class ManageUpdataOrganizationalUnitRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageUpdataOrganizationalUnitRoute.class);
    private Ldap ldap;
    @Inject
    public ManageUpdataOrganizationalUnitRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        OrganizationEntity organizationEntity= bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        String oldorganization = organizationEntity.getOldorganization();
        String oldorganizationalUnit = organizationEntity.getOldorganizationalUnit();
        String neworganizationalUnit = organizationEntity.getNeworganizationalUnit();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        ldap.connect();
        //修改组织单元
        System.out.println("修改组织单元");
        if (!ldap.isExistInLDAP("ou="+oldorganizationalUnit+",o="+oldorganization+dn)){
            System.out.println("[所修改组织单元不存在]");
            return "所修改组织单元不存在";
        }
        dn ="ou="+oldorganizationalUnit+",o="+oldorganization+dn;
        String newo="ou="+neworganizationalUnit;
        ldap.updateNodes(dn,newo);
        ldap.close();
        System.out.println("[成功修改组织单元]");
        return "成功修改组织单元";
    }
}
