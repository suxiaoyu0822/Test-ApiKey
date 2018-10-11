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

public class ManageQuoteUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageQuoteUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageQuoteUserRoute(Ldap ldap){
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
        String password = userEntity.getPassword();
        String description = userEntity.getDescription();
        String uid = userEntity.getUid();
        String dn = userEntity.getDn();
        ldap.connect();
        System.out.println("引用用户到其他组织");
        //添加引用字段属性uid，写明标识 description（empty无引用，BeQuote被引用，Quote引用）
        String sn = username.substring(1);
        ldap.addUidEntry(dn,sn,username,password,uid,description);
        ldap.close();
        System.out.println("[引用用户到其他组织成功]");
        return "引用用户到其他组织成功";
    }
}
