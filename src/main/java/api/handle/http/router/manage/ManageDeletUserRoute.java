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
 * @Date: Created in 18-10-9 上午9:33
 */

public class ManageDeletUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageDeletUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageDeletUserRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String uid = userEntity.getUid();
        String dn = userEntity.getDn();
        System.out.println("-----------------------------------删除用户信息-----------------------------------");

        ldap.connect();
        //判断是否为引用源,否-直接删除 是-无法删除，请先解除其他uid=dn的引用，再进行删除
        String Initials = null;
        try {
            Initials = ldap.searchInitials(uid,dn);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "删除用户失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        if (Initials.equals("BeQuote")){
            String string = "删除失败，请先解除其他引用，再进行删除";
            JSONObject jsonObject = JSONObject.fromObject(string);
            ldap.close();
            return jsonObject;
        }
        System.out.println("删除用户");
        ldap.delete("uid="+uid+","+dn);
        System.out.println("[成功删除用户]");
        ldap.close();
        String string = "成功删除用户！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        return jsonObject;

    }
}
