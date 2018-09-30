package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.OrganizationEntity;
import api.handle.dto.UserEntity;
import api.handle.ldap.Ldap;
import com.google.inject.Inject;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:用户信息管理
 * @Author:苏晓雨
 * @Date: Created in 18-9-26 下午3:26
 */

public class ManageUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageUserRoute(Ldap ldap){
        this.ldap=ldap;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        OrganizationEntity organizationEntity = bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        String handle = organizationEntity.getHandle();
        String oldorganization = organizationEntity.getOldorganization();
        String oldorganizationalUnit = organizationEntity.getOldorganizationalUnit();
        String neworganization = organizationEntity.getNeworganization();
        String neworganizationalUnit = organizationEntity.getNeworganizationalUnit();
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        String address = userEntity.getAddress();
        String telephoneNumber = userEntity.getTelephoneNumber();
        String email = userEntity.getEmail();
        String company = userEntity.getCompany();
        String keyword=userEntity.getKeyword();
        String newinfo = userEntity.getNewinfo();
        ldap.connect();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        if (handle.equals("创建")){
            System.out.println("创建用户");
            String sn = username.substring(1);
            ldap.addEntry(oldorganization,oldorganizationalUnit,sn,username,password,company,address,email,telephoneNumber);
            System.out.println("[成功添加用户]");
            ldap.close();
            return "成功添加用户";
        }else  if (handle.equals("删除")){
            System.out.println("删除用户");
            ldap.delete("cn="+username+",ou="+oldorganizationalUnit+",o="+oldorganization+dn);
            System.out.println("[成功删除用户]");
            return "成功删除用户";
        }else if (handle.equals("修改")){
            System.out.println("修改用户信息");
            dn = "cn="+username+",ou="+oldorganizationalUnit+",o="+oldorganization+dn;
            ldap.update(keyword,newinfo,dn);
            System.out.println("[修改用户信息成功]");
            return "修改用户信息成功";
        }else if (handle.equals("移动")){
            System.out.println("移动用户到其他组织");
            //获取原组织用户信息
            dn = "cn="+username+",ou="+oldorganizationalUnit+",o="+oldorganization+dn;
            List list=ldap.searchall(username,dn);
            Map<String,String> map = new LinkedHashMap<>();
            for (int i=0;i<list.size();i++){
                String key =StringUtils.substringBeforeLast(String.valueOf(list.get(i)),": ");
                String value =StringUtils.substringAfterLast(String.valueOf(list.get(i)),": ");
                map.put(key,value);
            }
            JSONObject jsonObject = JSONObject.fromObject(map);
            String tel =jsonObject.getString("telephoneNumber");
            String mail=jsonObject.getString("mail");
            String userPassword =jsonObject.getString("userPassword");
            String registeredAddress=jsonObject.getString("registeredAddress");
            String cn = jsonObject.getString("cn");
            String sn = jsonObject.getString("sn");
            String l = jsonObject.getString("l");
            //添加到新组织
            ldap.addEntry(neworganization,neworganizationalUnit,sn,cn,userPassword,registeredAddress,l,mail,tel);
            //删除原组织用户信息
            ldap.delete(dn);
            ldap.close();
            System.out.println("[移动用户到其他组织成功]");
            return "移动用户到其他组织成功";

        }else if (handle.equals("引用")){
            System.out.println("引用用户到其他组织");
            //获取原组织用户信息
            dn = "cn="+username+",ou="+oldorganizationalUnit+",o="+oldorganization+dn;
            List list=ldap.searchall(username,dn);
            Map<String,String> map = new LinkedHashMap<>();
            for (int i=0;i<list.size();i++){
                String key =StringUtils.substringBeforeLast(String.valueOf(list.get(i)),": ");
                String value =StringUtils.substringAfterLast(String.valueOf(list.get(i)),": ");
                map.put(key,value);
            }
            JSONObject jsonObject = JSONObject.fromObject(map);
            String tel =jsonObject.getString("telephoneNumber");
            String mail=jsonObject.getString("mail");
            String userPassword =jsonObject.getString("userPassword");
            String registeredAddress=jsonObject.getString("registeredAddress");
            String cn = jsonObject.getString("cn");
            String sn = jsonObject.getString("sn");
            String l = jsonObject.getString("l");
            //添加到新组织。
            ldap.addEntry(neworganization,neworganizationalUnit,sn,cn,userPassword,registeredAddress,l,mail,tel);
            ldap.close();
            System.out.println("[引用用户到其他组织成功]");
            return "引用用户到其他组织成功";
        }
    return "操作用户失败";
    }
}
