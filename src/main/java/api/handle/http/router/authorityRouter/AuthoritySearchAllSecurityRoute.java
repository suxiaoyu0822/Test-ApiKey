package api.handle.http.router.authorityRouter;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.ResourcePartEntity;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-14 下午1:34
 */

public class AuthoritySearchAllSecurityRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthoritySearchAllSecurityRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthoritySearchAllSecurityRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity= bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        String dn =resourcePartEntity.getDn();
//        String dn ="o=安全组,dc=resources,dc=baotoucloud,dc=com";
        System.out.println("-----------------------------------查询规则信息-----------------------------------");

        resourceLdap.connect();
        List listo= new ArrayList();
        List listou= new ArrayList();
        Map<String, String> map = new LinkedHashMap<>();
        Map<String, String> map2 = new LinkedHashMap<>();
        String Attributo = "security-id";
        String searchFilter = "objectClass=*";
        try {
            listo = resourceLdap.findRuleList(dn,searchFilter,Attributo);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "查询安全组信息失败,请重新操作!";
            JSONObject jsonObject = JSONObject.fromObject(string);
            resourceLdap.close();
            return jsonObject;
        }
        for (int i = 0;i<listo.size();i++){
            System.out.println("安全子组："+listo.get(i));
            searchFilter ="security-id="+listo.get(i);
            String[] Attribut = {"security-id","security-name","description","create-time"};
            listou = resourceLdap.FindAllSom(dn,searchFilter,Attribut);
            for (int j = 0;j<listou.size();j++){
                String key = StringUtils.substringBeforeLast(String.valueOf(listou.get(j)), ": ").replace("-","_");
                String value = StringUtils.substringAfterLast(String.valueOf(listou.get(j)), ": ");
//                System.out.println(key+"+"+value);
                map.put(key,value);
            }
            System.out.println("map:"+map);
            JSONObject jsonObject = JSONObject.fromObject(map);
            System.out.println("json1:"+jsonObject.toString());
            map2.put("security"+i,jsonObject.toString());
        }
        map2.put("number", String.valueOf(map2.size()));
        System.out.println(map2);
        JSONObject jsonObject = JSONObject.fromObject(map2);
        System.out.println(jsonObject);
        JSONObject jsonObject1 = ReturnJson.ReturnSuccessJson(jsonObject.toString());
        resourceLdap.close();
        return jsonObject1;
    }
}
