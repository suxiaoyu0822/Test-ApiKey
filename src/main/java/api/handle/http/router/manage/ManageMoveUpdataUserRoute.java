package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.UserEntity;
import api.handle.http.json.Json;
import api.handle.ldap.Ldap;
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
 * @Date: Created in 18-10-31 下午4:39
 */

public class ManageMoveUpdataUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageMoveUpdataUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageMoveUpdataUserRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String dn = userEntity.getDn();
        String newdn = userEntity.getNewdn();
        String username = userEntity.getCn();
        String password = userEntity.getUserPassword();
        String telephoneNumber = userEntity.getTelephoneNumber();
        String email = userEntity.getMail();
        String description = userEntity.getDescription();
        String uid= userEntity.getUid();
        String givenName =userEntity.getGivenName();
        String employeeType =userEntity.getEmployeeType();
        System.out.println("employeeType:"+employeeType);
        String initials = "empty";
        System.out.println("-----------------------------------添加用户信息-----------------------------------");

        ldap.connect();
        String sn = username.substring(0,1);
        try {
            ldap.addDNEntry(newdn,uid,sn,username,password,givenName,employeeType,initials,email,telephoneNumber,description);
            System.out.println("添加用户成功！");
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "添加用户失败,请重新操作!";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            ldap.close();
            return jsonObject;
        }
        System.out.println("修改并移动用户");
        try {
            ldap.delete("uid="+uid+","+dn);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "删除用户失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            ldap.close();
            return jsonObject;
        }
        System.out.println("[成功添加用户]");
        ldap.close();
        String string = "成功修改并移动用户";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        return jsonObject;
    }
}
