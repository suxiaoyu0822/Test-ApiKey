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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.naming.NamingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-9 上午9:34
 */

public class ManageMoveUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageMoveUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageMoveUserRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        OrganizationEntity organizationEntity = bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        String oldorganization = organizationEntity.getOldorganization();
        String oldorganizationalUnit = organizationEntity.getOldorganizationalUnit();
        String neworganization = organizationEntity.getNeworganization();
        String neworganizationalUnit = organizationEntity.getNeworganizationalUnit();
        String username = userEntity.getCn();
        System.out.println("-----------------------------------移动用户到其他组织-----------------------------------");

        ldap.connect();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        //获取原组织用户信息
        List list= null;
        try {
            list = ldap.searchall(username,"cn="+username+",ou="+oldorganizationalUnit+",o="+oldorganization+dn);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "移动用户到其他组织失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        Map<String,String> map = new LinkedHashMap<>();
        for (int i=0;i<list.size();i++){
            String key =StringUtils.substringBeforeLast(String.valueOf(list.get(i)),": ");
            String value =StringUtils.substringAfterLast(String.valueOf(list.get(i)),": ");
            map.put(key,value);
        }
        System.out.println(map);
        JSONObject jsonObject = JSONObject.fromObject(map);
        String tel =jsonObject.getString("telephoneNumber");
        String mail=jsonObject.getString("mail");
        String userPassword =jsonObject.getString("userPassword");
        String registeredAddress=jsonObject.getString("registeredAddress");
        String cn = jsonObject.getString("cn");
        String sn = jsonObject.getString("sn");
        String l = jsonObject.getString("l");
        String description = jsonObject.getString("description");
        //判断要移动的用户是否为引用源,否-直接移动删除 是-无法移动，请先解除其他uid=dn的引用，再进行移动
        if (description.equals("BeQuote")){
            String string = "移动失败，请先解除其他uid="+"cn="+username+",ou="+oldorganizationalUnit+",o="+oldorganization+dn+"的引用，再进行移动";
            JSONObject jsonObject1 = ReturnJson.ReturnFailJson(string);
            return jsonObject1;
        }
        //添加到新组织
        ldap.addEntry(neworganization,neworganizationalUnit,sn,cn,userPassword,registeredAddress,l,mail,tel,description);
        //删除原组织用户信息
        System.out.println("删除用户");
        ldap.delete("cn="+username+",ou="+oldorganizationalUnit+",o="+oldorganization+dn);
        System.out.println("[成功删除用户]");
        ldap.close();
        System.out.println("[移动用户到其他组织成功]");
        String string = "移动用户到其他组织成功";
        JSONObject jsonObject2 = ReturnJson.ReturnSuccessJson(string);
        return jsonObject2;
    }
}
