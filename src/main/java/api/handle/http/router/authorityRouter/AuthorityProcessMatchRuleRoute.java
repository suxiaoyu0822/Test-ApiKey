package api.handle.http.router.authorityRouter;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.ResourcePartEntity;
import api.handle.util.MyComparator;
import api.handle.util.ReturnJudge;
import api.handle.util.TimeStampAndLongUtil;
import com.google.gson.Gson;
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

import java.util.*;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-5 上午10:17
 */

public class AuthorityProcessMatchRuleRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityProcessMatchRuleRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityProcessMatchRuleRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        String resources_id =resourcePartEntity.getResources_id();
        String client_ip_range =resourcePartEntity.getClient_ip_range();
        String server_ip_range =resourcePartEntity.getServer_ip_range();
        String now_time =resourcePartEntity.getNow_time();
        String user_name =resourcePartEntity.getUser_name();
        String group_name =resourcePartEntity.getGroup_name();
        System.out.println("-----------------------------------匹配规则-----------------------------------");
        String dn ="ou=API,o=资源,dc=resources,dc=baotoucloud,dc=com";
        System.out.println("resources_id:"+resources_id);
        resourceLdap.connect();
        //根据资源id查找安全组id
        String searchFilter = "resources-id="+resources_id;
        String retAttrs = "security-id";
        String security_id =resourceLdap.find(dn,searchFilter,retAttrs);
        System.out.println("security_id:"+security_id);
        //根据安全组id 查找规则
        dn = "security-id="+security_id+",o=安全组,dc=resources,dc=baotoucloud,dc=com";
        resourcePartEntity.setDn(dn);
        List rule_id =resourceLdap.FindAllAttribut(resourcePartEntity);
        Map<String, String> map = new LinkedHashMap<>();
        Map<String,String> stu=new TreeMap<>(new MyComparator());//传进来一个key的比较器对象来构造treemap
        for (int i=0;i<rule_id.size();i++){
            System.out.println("---------------------------------------------");
            System.out.println("rule-id:"+rule_id.get(i));
            resourcePartEntity.setRule_id(String.valueOf(rule_id.get(i)));
            List list1 = resourceLdap.FindAll(resourcePartEntity);
            for (int j = 0; j < list1.size(); j++) {
//                System.out.println(list1.get(j));
                String key = StringUtils.substringBeforeLast(String.valueOf(list1.get(j)), ": ");
                String value = StringUtils.substringAfterLast(String.valueOf(list1.get(j)), ": ");
                map.put(key,value);
            }
            TimeStampAndLongUtil timeStampAndLongUtil = new TimeStampAndLongUtil();
            String a = String.valueOf(timeStampAndLongUtil.TimeToLong(map.get("create-time")));
//            System.out.println(map.toString());
            JSONObject jsonObject = JSONObject.fromObject(map);
            stu.put(a,jsonObject.toString());
            System.out.println("创建时间戳:"+a);

        }
        System.out.println("---------------------------------------------");
        Set<String> keySet=stu.keySet();
        Iterator it=keySet.iterator();
        String action = "drop";
        //根据时间排序后规则匹配
        while (it.hasNext()) {
            String next = (String)it.next();
            System.out.println(next+","+stu.get(next));
            JSONObject jsonObject = JSONObject.fromObject(stu.get(next));
//            System.out.println(jsonObject.getString("action"));
            ReturnJudge returnJudge = new ReturnJudge();
//            System.out.println("action1："+action);
            action =returnJudge.Judge(jsonObject,client_ip_range,server_ip_range,now_time,user_name,group_name,action);
            System.out.println("action："+action);
        }
        //返回匹配结果
        resourceLdap.close();
        return action;
    }
}
