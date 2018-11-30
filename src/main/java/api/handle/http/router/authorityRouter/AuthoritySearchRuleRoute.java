package api.handle.http.router.authorityRouter;

import api.handle.authority.acl.Acl;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-22 上午10:44
 */

public class AuthoritySearchRuleRoute implements Route{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthoritySearchRuleRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthoritySearchRuleRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        System.out.println("-----------------------------------查询规则-----------------------------------");

        resourceLdap.connect();
        Map<String, String> map = new LinkedHashMap<>();
        List list = null;
        try {
            list = resourceLdap.FindAllAttribut(resourcePartEntity);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "查询规则失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            resourceLdap.close();
            return jsonObject;
        }
        Map<String, String> mapList = new LinkedHashMap<>();
        for (int i = 0;i<list.size();i++){

            resourcePartEntity.setRule_id(String.valueOf(list.get(i)));
            List list1 = resourceLdap.FindAll(resourcePartEntity);
            for (int j = 0; j < list1.size(); j++) {
                String key = StringUtils.substringBeforeLast(String.valueOf(list1.get(j)), ": ").replace("-","_");
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
            mapList.put("rule"+i,jsonObject.toString());
        }
        mapList.put("number", String.valueOf(mapList.size()));
        System.out.println("list1:"+mapList);
        JSONObject jsonObject = JSONObject.fromObject(mapList);
//        System.out.println(jsonObject.size());
//        System.out.println("json2:"+jsonObject.toString());
//        System.out.println("number:"+jsonObject.get("number"));
        resourceLdap.close();
        JSONObject jsonObject1 = ReturnJson.ReturnSuccessJson(jsonObject.toString());
        return jsonObject1;
    }
}
