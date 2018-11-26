package api.manage;

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
 * @Date: Created in 18-10-26 下午5:21
 */

public class SearchAllClient {
    public static void main(String[] args) throws IOException {
//        String oldorganization = "公安";
//        String oldorganizationalUnit="通信处";
//        String username = "苏晓雨";
////        String username = "吕献军";
////        String username = "金鹏程";
//        String password = "123456";
//        String address = "包头";
//        String telephoneNumber = "13122223333";
//        String company = "云计算中心";
//        String email = "123456789@yahoo.com";
//        String uid="cn=苏晓雨,ou=科技一组,ou=科技处,o=公安,dc=registry,dc=baotoucloud,dc=com";
        String dn = "dc=registry,dc=baotoucloud,dc=com";
//        String description = "empty";
//        String description = "Quote";
//        String description = "BeQuote";
        Map<Object, Object> map = new HashMap<>();
        map.put("dn",dn);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://192.168.101.151:8083/api/manage/SearchAll";
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
//        JSONObject a = JSONObject.fromObject(resEntity);
        System.out.println(a);
    }
}
