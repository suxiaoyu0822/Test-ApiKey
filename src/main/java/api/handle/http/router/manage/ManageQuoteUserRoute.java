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

public class ManageQuoteUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageQuoteUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageQuoteUserRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String username = userEntity.getCn();
        String password = userEntity.getUserPassword();
        String description = userEntity.getDescription();
        String uid = userEntity.getUid();
        String dn = userEntity.getDn();
        System.out.println("-----------------------------------引用用户到其他组织-----------------------------------");

        ldap.connect();
        //添加引用字段属性uid，写明标识 description（empty无引用，BeQuote被引用，Quote引用）
        String sn = username.substring(1);
        try {
            ldap.addUidEntry(dn,sn,username,password,uid,description);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "引用用户到其他组织失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            ldap.close();
            return jsonObject;
        }
        ldap.close();
        System.out.println("[引用用户到其他组织成功]");
        String string = "引用用户到其他组织成功！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        return jsonObject;
    }
}
