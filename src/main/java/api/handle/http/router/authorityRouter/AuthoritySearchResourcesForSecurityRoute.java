package api.handle.http.router.authorityRouter;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.ResourcePartEntity;
import api.handle.mysql.impl.GetMysqlClusterDaoImpl;
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

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-19 下午2:59
 */

public class AuthoritySearchResourcesForSecurityRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthoritySearchResourcesForSecurityRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthoritySearchResourcesForSecurityRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        long apiversionid = resourcePartEntity.getApiversionid();
        System.out.println("apiversionid:"+apiversionid);
        System.out.println("-----------------------------------查询资源绑定的安全组信息！-----------------------------------");
//        long apiversionid = 2482;
        String security_id =null;
        String sql ="SELECT security_id FROM `video-cloud-base-platform`.api_version where id ='"+apiversionid+"'";
        GetMysqlClusterDaoImpl getMysqlClusterDao = new GetMysqlClusterDaoImpl();
        ResultSet rs= getMysqlClusterDao.GetSQLCluster(sql);
        while (rs.next()){
//            System.out.println(rs.getString("security_id"));
            security_id=rs.getString("security_id");
        }
        System.out.println("security_id:"+security_id);
        Map<String,String> map = new LinkedHashMap<>();
        String dn ="o=安全组,dc=resources,dc=baotoucloud,dc=com";
        String searchFilter ="security-id="+security_id;
        String[] Attribut = {"security-id","security-name","description","create-time"};
        resourceLdap.connect();
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
        JSONObject jsonObject1 = ReturnJson.ReturnSuccessJson(jsonObject.toString());
        System.out.println("jsonObject1:"+jsonObject1);
        resourceLdap.close();
        return jsonObject1;
    }
}
