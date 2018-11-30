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
import java.util.Date;

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
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String cn = userEntity.getCn();
        String password = userEntity.getUserPassword();
        String telephoneNumber = userEntity.getTelephoneNumber();
        String email = userEntity.getMail();
        String description = userEntity.getDescription();
        String dn =userEntity.getDn();
        Date date = new Date();
        String uid= String.valueOf(date.getTime());
        String givenName =userEntity.getGivenName();
        String employeeType =userEntity.getEmployeeType();
        System.out.println("employeeType:"+employeeType);
        String initials = "empty";
        System.out.println("-----------------------------------创建用户-----------------------------------");

        ldap.connect();
        String sn = cn.substring(0,1);


        try {
            ldap.addDNEntry(dn,uid,sn,cn,password,givenName,employeeType,initials,email,telephoneNumber,description);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "添加用户失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            ldap.close();
            return jsonObject;
        }
        System.out.println("[成功添加用户]");
        ldap.close();
        String string = "成功添加用户";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        return jsonObject;
    }
}
