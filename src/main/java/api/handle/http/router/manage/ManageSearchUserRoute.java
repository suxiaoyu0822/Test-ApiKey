package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.OrganizationEntity;
import api.handle.dto.UserEntity;
import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;
import com.google.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String dn =userEntity.getDn();
        ldap.connect();
        System.out.println("查询ou下的cn及属性");
        Map<String, String> map = new LinkedHashMap<>();
        String Attribut = "cn";
        List list=ldap.searchForAttribut(Attribut,dn);
        List<String> mapList = new ArrayList<>();
        for (int i=0;i<list.size();i++) {
            map.clear();
            System.out.println(list.get(i));
            //判断description是否为Quote 否-不是引用源；是-是引用源，找到uid，判断引用源是否存在 否-不存在，删除此用户，是-存在，继续下一个用户
            String ret = "description";
            String description = ldap.searchOne(String.valueOf(list.get(i)), dn,ret);
            if (description.equals("Quote")){
                ret = "uid";
                String uid = ldap.searchOne(String.valueOf(list.get(i)), dn,ret);
                if (!ldap.isExistInLDAP(uid)){
                    System.out.println("cn="+list.get(i)+"为引用，并且引用源不存在，跳出本次循环并删除此用户");
                    ldap.delete("cn="+list.get(i)+","+dn);
                    continue;
                }else {
                    System.out.println("cn="+list.get(i)+"为引用，并且引用源存在！");
                }
            }
            List list1 = ldap.searchall(String.valueOf(list.get(i)), dn);
            for (int j = 0; j < list1.size(); j++) {
                String key = StringUtils.substringBeforeLast(String.valueOf(list1.get(j)), ": ");
                String value = StringUtils.substringAfterLast(String.valueOf(list1.get(j)), ": ");
//                System.out.println(key+"+"+value);
                map.put(key, value);
//                System.out.println(map);

            }
            System.out.println("=="+map);
            String str = map.toString();
            mapList.add(str);
        }
        return mapList.toString();
    }
}
