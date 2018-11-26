package api.before;

import api.handle.util.GetDigestUtil;
import api.handle.util.NonceRandomUtil;
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
 * @Date: Created in 18-9-12 下午5:12
 */

public class UserRegisterClient {
    public static void main(String[] args) throws IOException {
        String username = "sxy";
        String password = "123456";
        String sex = "man";
        String address = "baotou";
        String telephoneNumber = "123456789";
        String organization = "gongan";
        String organizationalUnit="kejichu";
        String company = "anyun";
        String email = "123456789@yahoo.com";
        //等等
        NonceRandomUtil nonceRandomUtil = new NonceRandomUtil();
        String clientNonce = nonceRandomUtil.randomString(16);
        System.out.println("clientNonce: "+clientNonce);
        String uri = "/api/user/Register";
        String reaml = "qwer";

        Map<Object, Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        map.put("sex",sex);
        map.put("address",address);
        map.put("telephoneNumber",telephoneNumber);
        map.put("organization",organization);
        map.put("organizationalUnit",organizationalUnit);
        map.put("company",company);
        map.put("email",email);
        map.put("clientNonce",clientNonce);
        map.put("uri",uri);
        map.put("reaml",reaml);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://localhost:8083/api/user/Register";
        HttpPost httppost = new HttpPost(url);
        GetDigestUtil getDigestUtil = new GetDigestUtil();
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setHeader("Authorization",getDigestUtil.GetResponse(map));
        System.out.println("Authorization: "+getDigestUtil.GetResponse(map));
        httppost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println(a);
    }


}
