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
 * @Date: Created in 18-10-9 上午9:32
 */

public class ManageAddOrganizationalUnitRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageAddOrganizationalUnitRoute.class);
    private Ldap ldap;
    @Inject
    public ManageAddOrganizationalUnitRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String dn = userEntity.getDn();
        ldap.connect();
        //创建组织单元
        System.out.println("创建组织单元");
        if (ldap.isExistInLDAP(dn)){
            System.out.println("[所创建组织单元已存在]");
            return "所创建组织单元已存在";
        }
        ldap.addOUDN(dn);
        ldap.close();
        System.out.println("[成功创建组织单元]");
        return "成功创建组织单元";
    }
}
