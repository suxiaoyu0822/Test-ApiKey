package api.authorityTest;

import com.google.gson.annotations.SerializedName;
import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-22 下午2:56
 */

public class ResourceManageClient {
    public static void main(String[] args) throws IOException {
        String oldorganizational="安全组";
        String newoldorganizational="资源";

        String security_name="安全二组";
        String resources_name="API3";
        String security_id ="1542162118080";
        String resources_id ="202";
        String description ="描述";

        String rule_id ="1234567";
        String client_ip_range = "192.168.88.0/24";
        String server_ip_range = "192.168.77.0/24";
        String start_time = "2018-10-05 15:00:18";
        String end_time = "2018-10-08 15:20:18";
        String create_time = "无";
        String user_name ="吕献军";
        String group_name = "大军";
        String action = "normal";
//        String action = "drop";
//        String dn ="security-id=1542162156746,o=安全组,dc=resources,dc=baotoucloud,dc=com";
        String dn ="ou=API,o=资源,dc=resources,dc=baotoucloud,dc=com";
            String newdn ="userid=1234567,ou=API,o=resource,dc=resources,dc=baotoucloud,dc=com";
        String updtkey = "";
        String updt = "";
        Map<Object, Object> map = new HashMap<>();
        map.put("oldorganizational",oldorganizational);
        map.put("newoldorganizational",newoldorganizational);
        map.put("security_name",security_name);
        map.put("resources_name",resources_name);
        map.put("security_id",security_id);
        map.put("resources_id",resources_id);
        map.put("description",description);
        map.put("rule_id",rule_id);
        map.put("client_ip_range",client_ip_range);
        map.put("server_ip_range",server_ip_range);
        map.put("start_time",start_time);
        map.put("end_time",end_time);
        map.put("create_time",create_time);
        map.put("user_name",user_name);
        map.put("group_name",group_name);
        map.put("action",action);
        map.put("dn",dn);
        map.put("newdn",newdn);
        map.put("updtkey",updtkey);
        map.put("updt",updt);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
//        String url = "http://localhost:8083/api/authority/AddResourcesOU";
        String url = "http://localhost:8083/api/authority/CoverResourcesOU";
//        String url = "http://localhost:8083/api/authority/AddSecurityOU";
//        String url = "http://localhost:8083/api/authority/AddRule";
//        String url = "http://localhost:8083/api/authority/DeletRule";
//        String url = "http://localhost:8083/api/authority/DeletNode";
//        String url = "http://localhost:8083/api/authority/UpdateRule";
//        String url = "http://localhost:8083/api/authority/UpdateNode";
//        String url = "http://localhost:8083/api/authority/SearchRule";
//        String url = "http://localhost:8083/api/authority/SearchAllOrgnization";
//        String url = "http://localhost:8083/api/authority/SearchAllSecurity";
//        String url = "http://localhost:8083/api/authority/InverseQuery";
//        String url = "http://localhost:8083/api/authority/SearchResourcesForSecurity";
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println(a);
    }
}
