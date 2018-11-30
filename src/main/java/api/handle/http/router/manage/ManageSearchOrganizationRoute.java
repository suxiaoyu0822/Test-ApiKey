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
import java.util.*;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-9 上午9:32
 */

public class ManageSearchOrganizationRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageSearchOrganizationRoute.class);
    private Ldap ldap;
    @Inject
    public ManageSearchOrganizationRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String dn =userEntity.getDn();
        System.out.println("-----------------------------------查询根下的o-----------------------------------");

        ldap.connect();
        String Attribut = "o";
        List list= null;
        String value = null;
        Map<String,String> map = new HashMap<>();
        try {
            list = ldap.searchForAttribut(Attribut,dn);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "查询组织失败,请重新操作！";
            System.out.println(string);
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            ldap.close();
            return jsonObject;
        }
        for (int i=0;i<list.size();i++) {
            value = String.valueOf(list.get(i));
            map.put("o"+i,value);
        }
        System.out.println("查询组织成功！");
        map.put("number", String.valueOf(map.size()));
        System.out.println(map);
        JSONObject jsonObject = JSONObject.fromObject(map);
        System.out.println(jsonObject);
        ldap.close();
        return jsonObject;
    }
}
