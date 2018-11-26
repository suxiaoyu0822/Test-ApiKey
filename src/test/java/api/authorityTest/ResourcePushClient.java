package api.authorityTest;

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
 * @Date: Created in 18-11-5 上午10:29
 */

public class ResourcePushClient {
    public static void main(String[] args) throws IOException {
        String resources_id ="App-v1-Add1";
        String client_ip_range = "192.168.121.1";
        String server_ip_range = "192.168.11.1";
        String now_time = "2018-10-06 00:00:00";
        String user_name ="金鹏程";
        String group_name = "大金";
        String dn ="ou=API,o=资源,dc=resources,dc=baotoucloud,dc=com";
        Map<Object, Object> map = new HashMap<>();
        map.put("resources_id",resources_id);
        map.put("client_ip_range",client_ip_range);
        map.put("server_ip_range",server_ip_range);
        map.put("now_time",now_time);
        map.put("user_name",user_name);
        map.put("group_name",group_name);
        map.put("dn",dn);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://localhost:8083/api/authority/MatchProcess";
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println(a);
    }
}
