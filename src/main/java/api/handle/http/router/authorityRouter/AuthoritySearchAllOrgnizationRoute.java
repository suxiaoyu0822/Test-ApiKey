package api.handle.http.router.authorityRouter;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.ResourcePartEntity;
import com.google.inject.Inject;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-14 上午10:30
 */

public class AuthoritySearchAllOrgnizationRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthoritySearchAllOrgnizationRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthoritySearchAllOrgnizationRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public JSON handle(Request request, Response response) throws Exception {

        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity= bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
//        String dn =resourcePartEntity.getDn();
        String dn ="dc=resources,dc=baotoucloud,dc=com";
        System.out.println("-----------------------------------查询所有-----------------------------------");

        resourceLdap.connect();
        System.out.println("查询根下的o");
        String Attributo = "o";
        List listo= null;
        String valueo = null;
        String valueou = null;
        String valueouo = null;
        String Attributou = null;
        String Attributouo = null;
        String searchFilter = "objectClass=*";
        Map<String,String> map = new HashMap<>();
        Map<String,String> mapall = new HashMap<>();
        try {
            listo = resourceLdap.findRuleList(dn,searchFilter,Attributo);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "查询失败,请重新操作!";
            JSONObject jsonObject = JSONObject.fromObject(string);
            return jsonObject;
        }
        for (int i=0;i<listo.size();i++) {
            Map<String,String> map1 = new HashMap<>();
            valueo = String.valueOf(listo.get(i));
            System.out.println("查询o下的ou："+"o="+valueo+","+dn);
            System.out.println("组织节点："+valueo);
            List listou= null;
            if (valueo.equals("安全组")){
                Attributou = "security-id";
            }else {
                Attributou = "ou";
            }
            listou = resourceLdap.findRuleList("o="+valueo+","+dn,searchFilter,Attributou);
            System.out.println("查询o下的ou个数："+listou.size());
            for (int j=0;j<listou.size();j++){
                Map<String,String> map2 = new HashMap<>();
                valueou = String.valueOf(listou.get(j));
                System.out.println("查询ou下的ou："+"ou="+valueou+",o="+valueo+","+dn);
                System.out.println("组织单元节点："+valueou);
                List listouo= null;
                if (valueo.equals("安全组")){
                    Attributouo = "rule-id";
                    listouo = resourceLdap.findRuleList("security-id=" + valueou + ",o=" + valueo + "," + dn, searchFilter, Attributouo);
                }else {
                    Attributouo = "resources-id";
                    listouo = resourceLdap.findRuleList("ou=" + valueou + ",o=" + valueo + "," + dn, searchFilter, Attributouo);
                }
                System.out.println("查询ou下的ou个数："+listouo.size());
                for (int t=0;t<listouo.size();t++){
                    valueouo = String.valueOf(listouo.get(t));
                    System.out.println("组织单元子节点："+valueouo);
                    map2.put("ouo"+i+j+t,valueouo);
                }
                map2.put("numberouo", String.valueOf(listouo.size()));
                JSONObject jsonObjectou = JSONObject.fromObject(map2);
                mapall.clear();
                mapall.put("title",valueou);
                mapall.put("value",jsonObjectou.toString());
                JSONObject jsonObjectall = JSONObject.fromObject(mapall);
                map1.put("ou"+i+j,jsonObjectall.toString());
            }
            map1.put("numberou", String.valueOf(listou.size()));
            JSONObject jsonObject = JSONObject.fromObject(map1);
            mapall.clear();
            mapall.put("title",valueo);
            mapall.put("value",jsonObject.toString());
            JSONObject jsonObjectall = JSONObject.fromObject(mapall);
            map.put("o"+i,jsonObjectall.toString());
        }
//        System.out.println("map2:"+map2);
//        System.out.println("map1:"+map1);
        map.put("numbero", String.valueOf(listo.size()));
        System.out.println("map:"+map);
        System.out.println("查询成功！");
        JSONObject jsonObject = JSONObject.fromObject(map);
        System.out.println("json:"+jsonObject);
        return jsonObject;
    }
}
