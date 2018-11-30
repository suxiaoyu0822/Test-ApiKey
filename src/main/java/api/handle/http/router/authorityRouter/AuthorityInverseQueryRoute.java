package api.handle.http.router.authorityRouter;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.ResourcePartEntity;
import api.handle.util.ReturnJson;
import com.google.inject.Inject;
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
 * @Date: Created in 18-11-14 上午11:21
 */

public class AuthorityInverseQueryRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityInverseQueryRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityInverseQueryRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }

    @Override
    public Object handle(Request request, Response response)throws Exception  {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        //获取到安全组子组id的security-id
        System.out.println("-----------------------------------（反向查询）查询安全子组所绑定的资源信息！-----------------------------------");
        resourceLdap.connect();
        String dn = "o=资源,dc=resources,dc=baotoucloud,dc=com";
        System.out.println(resourcePartEntity.getSecurity_id());
        String searchFilter = "security-id="+resourcePartEntity.getSecurity_id();
        String retAttrs = "resources-id";
        //所绑定的资源节点信息

        List list = null;
        try {
            list = resourceLdap.findAllResourceList(dn,searchFilter,retAttrs);
        } catch (NamingException e) {
//            e.printStackTrace();
            System.out.println("该安全子组没有绑定任何资源，请重新操作！");
            String string = "该安全子组没有绑定任何资源，请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            resourceLdap.close();
            return jsonObject;
        }
        Map<String, String> map = new LinkedHashMap<>();
        Map<String, String> map2 = new LinkedHashMap<>();
        for (int i = 0;i<list.size();i++){
            System.out.println("绑定的资源id："+list.get(i));
            searchFilter ="resources-id="+list.get(i);
            String[] Attribut = {"resources-id","resources-name","security-id","security-name","description","create-time"};
            List listou = resourceLdap.FindAllSom(dn,searchFilter,Attribut);
            for (int j = 0;j<listou.size();j++){
                String key = StringUtils.substringBeforeLast(String.valueOf(listou.get(j)), ": ").replace("-","_");
                String value = StringUtils.substringAfterLast(String.valueOf(listou.get(j)), ": ");
//                System.out.println(key+"+"+value);
                map.put(key,value);
            }
            System.out.println("map:"+map);
            JSONObject jsonObject = JSONObject.fromObject(map);
            System.out.println("json1:"+jsonObject.toString());
            map2.put("resources"+i,jsonObject.toString());
        }
        map2.put("number", String.valueOf(map2.size()));
        System.out.println("map2:"+map2);
        JSONObject jsonObject = JSONObject.fromObject(map2);
        JSONObject jsonObject1 = ReturnJson.ReturnSuccessJson(jsonObject.toString());
        System.out.println("返回结果："+jsonObject1);
        return jsonObject1;
    }
}
