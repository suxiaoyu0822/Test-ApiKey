package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.OrganizationEntity;
import api.handle.dto.UserEntity;
import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;
import api.handle.util.ReturnJson;
import com.google.inject.Inject;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
 * @Date: Created in 18-10-9 上午9:34
 */

public class ManageSearchUserRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageSearchUserRoute.class);
    private Ldap ldap;
    @Inject
    public ManageSearchUserRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String dn =userEntity.getDn();
//        String dn ="ou=keyuan,ou=zuzhibu,o=zhengfu,dc=registry,dc=baotoucloud,dc=com";
        System.out.println("-----------------------------------查询ou下的cn及属性-----------------------------------");

        ldap.connect();
        Map<String, String> map = new LinkedHashMap<>();
//        List map = new ArrayList();
        String Attribut = "uid";
        List list= null;
        try {
            list = ldap.searchForAttribut(Attribut,dn);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "查询失败,请重新操作！";
            System.out.println(string);
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
//        List<String> mapList = new ArrayList<>();
        Map<String, String> mapList = new LinkedHashMap<>();
//        Map<String, List> mapList = new LinkedHashMap<>();
        for (int i=0;i<list.size();i++) {
            map.clear();
            System.out.println(list.get(i));
            //判断description是否为Quote 否-不是引用源；是-是引用源，找到uid，判断引用源是否存在 否-不存在，删除此用户，是-存在，继续下一个用户
            String ret = "initials";
            String initials = ldap.searchOne(String.valueOf(list.get(i)), dn,ret);
            if (initials.equals("Quote")){
                ret = "postalAddress";
                String uid = ldap.searchOne(String.valueOf(list.get(i)), dn,ret);
                if (!ldap.isExistInLDAP(uid)){
                    System.out.println("uid="+list.get(i)+"为引用，并且引用源不存在，跳出本次循环并删除此用户");
                    ldap.delete("uid="+list.get(i)+","+dn);
                    continue;
                }else {
                    System.out.println("uid="+list.get(i)+"为引用，并且引用源存在！");
                }
            }
            List list1 = ldap.searchall(String.valueOf(list.get(i)), dn);
            for (int j = 0; j < list1.size(); j++) {
                String key = StringUtils.substringBeforeLast(String.valueOf(list1.get(j)), ": ");
                String value = StringUtils.substringAfterLast(String.valueOf(list1.get(j)), ": ");
//                System.out.println(key+"+"+value);
//                System.out.println(list1.get(j));
                map.put(key, value);
//                map.add(list1.get(j));
//                System.out.println(map);

            }
            System.out.println("map:"+map);
            JSONObject jsonObject = JSONObject.fromObject(map);
            System.out.println("json1:"+jsonObject.toString());
            mapList.put("user"+i,jsonObject.toString());
        }
        mapList.put("number", String.valueOf(mapList.size()));
        System.out.println("list1:"+mapList);
        JSONObject jsonObject = JSONObject.fromObject(mapList);
        System.out.println(jsonObject.size());
        System.out.println("json2:"+jsonObject.toString());
        System.out.println("number:"+jsonObject.get("number"));
//        JSONObject jsonObject1 = JSONObject.fromObject(jsonObject.get("user0"));
//        System.out.println(jsonObject1.size());
//        System.out.println(jsonObject1.toString(1));
//        System.out.println("uid:"+jsonObject1.getString("uid"));
//        System.out.println("jsonObject:"+jsonObject);
        return jsonObject;
    }
}
