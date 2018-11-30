package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.OrganizationEntity;
import api.handle.dto.UserEntity;
import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;
import api.handle.util.ReturnJson;
import com.google.inject.Inject;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.naming.NamingException;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-9 上午9:34
 */

public class ManageUpdataUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageUpdataUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageUpdataUserRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String uid = userEntity.getUid();
        String cn = userEntity.getCn();
        String dn = userEntity.getDn();
        String userPassword = userEntity.getUserPassword();
        String givenName = userEntity.getGivenName();
        String employeeType = userEntity.getEmployeeType();
        String initials = userEntity.getInitials();
        String mail = userEntity.getMail();
        String telephoneNumber = userEntity.getTelephoneNumber();
        String description = userEntity.getDescription();
        System.out.println("-----------------------------------修改用户信息-----------------------------------");

        ldap.connect();
        dn = "uid="+uid+","+dn;
        try {
            ldap.update(cn,userPassword,givenName,employeeType,initials,mail,telephoneNumber,description,dn);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "修改用户信息失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            ldap.close();
            return jsonObject;
        }
        System.out.println("[修改用户信息成功]");
        String string = "修改用户信息成功!";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        ldap.close();
        return jsonObject;
    }
}
