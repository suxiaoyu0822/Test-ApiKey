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

public class ManageAddUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageAddUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageAddUserRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        String address = userEntity.getAddress();
        String telephoneNumber = userEntity.getTelephoneNumber();
        String email = userEntity.getEmail();
        String description = userEntity.getDescription();
        String company = userEntity.getCompany();
        String dn =userEntity.getDn();
        ldap.connect();
        System.out.println("创建用户");
        String sn = username.substring(1);
        ldap.addDNEntry(dn,sn,username,password,company,address,email,telephoneNumber,description);
        System.out.println("[成功添加用户]");
        ldap.close();
        return "成功添加用户";
    }
}
