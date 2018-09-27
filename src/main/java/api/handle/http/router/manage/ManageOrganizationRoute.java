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
 * @Date: Created in 18-9-26 下午3:25
 */

public class ManageOrganizationRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageOrganizationRoute.class);
    private Ldap ldap;
    @Inject
    public ManageOrganizationRoute(Ldap ldap){
        this.ldap=ldap;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        OrganizationEntity organizationEntity= bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        String handle =organizationEntity.getHandle();
        String oldorganization = organizationEntity.getOldorganization();
        String neworganization = organizationEntity.getNeworganization();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        ldap.connect();
        if (handle.equals("创建")){
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
        }else if (handle.equals("删除")){
            //删除组织
            System.out.println("删除组织");
            if (!ldap.isExistInLDAP("o="+oldorganization+dn)){
                System.out.println("[所删除组织不存在]");
                return "所删除组织不存在";
            }
            dn ="o="+oldorganization+dn;
            ldap.delete(dn);
            ldap.close();
            System.out.println("[成功删除组织]");
            return "成功删除组织";
        }else if (handle.equals("修改")){
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
        return "操作组织失败";
    }

}
