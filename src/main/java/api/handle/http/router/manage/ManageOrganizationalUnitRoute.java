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
 * @Description:组织单元管理
 * @Author:苏晓雨
 * @Date: Created in 18-9-27 上午9:20
 */

public class ManageOrganizationalUnitRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageOrganizationalUnitRoute.class);
    private Ldap ldap;
    @Inject
    public ManageOrganizationalUnitRoute(Ldap ldap){
        this.ldap=ldap;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        OrganizationEntity organizationEntity= bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        String handle =organizationEntity.getHandle();
        String oldorganization = organizationEntity.getOldorganization();
        String oldorganizationalUnit = organizationEntity.getOldorganizationalUnit();
        String neworganizationalUnit = organizationEntity.getNeworganizationalUnit();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        ldap.connect();
        if (handle.equals("创建")){
            //创建组织单元
            System.out.println("创建组织单元");
            if (ldap.isExistInLDAP("ou="+oldorganizationalUnit+",o="+oldorganization+dn)){
                System.out.println("[所创建组织单元已存在]");
                return "所创建组织单元已存在";
            }
            ldap.addOU(oldorganization,oldorganizationalUnit);
            ldap.close();
            System.out.println("[成功创建组织单元]");
            return "成功创建组织单元";
        }else if (handle.equals("删除")){
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
        }else if (handle.equals("修改")){
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
        return "操作组织单元失败";
    }

}
