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

public class ManageDeletOrganizationalUnitRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageDeletOrganizationalUnitRoute.class);
    private Ldap ldap;
    @Inject
    public ManageDeletOrganizationalUnitRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        OrganizationEntity organizationEntity= bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        String oldorganization = organizationEntity.getOldorganization();
        String oldorganizationalUnit = organizationEntity.getOldorganizationalUnit();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        ldap.connect();
        //删除组织单元
        System.out.println("删除组织单元");
        if (!ldap.isExistInLDAP("ou="+oldorganizationalUnit+",o="+oldorganization+dn)){
            System.out.println("[所删除组织单元不存在]");
            return "所删除组织单元不存在";
        }
        dn ="ou="+oldorganizationalUnit+",o="+oldorganization+dn;
        ldap.delete(dn);
        ldap.close();
        System.out.println("[成功删除组织单元]");
        return "成功删除组织单元";
    }
}
