package api.jwt;

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
 * @Date: Created in 18-11-22 下午5:21
 */

public class AboutJwtClient {
    public static void main(String[] args) throws IOException {
        String appId = "123456";
        String alg = "HS256";               //加密算法
        String typ = "JWT";                 //类型
        String iss = "苏下雨";               //签发者
        String sub = "苏大于";               //面向的用户
        String aud = "苏暴雨";               //接收者
        String nbf = "0";                   //什么时间之前不可用（大于等于签发时间）
        String exp = "10";                  //过期时间
        String iat = "2018-11-22";          //签发时间
        String jti = "qwer";                //jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
        String dn = "o=应用,dc=resources,dc=baotoucloud,dc=com";
//        String dn = "appId=123456,o=应用,dc=resources,dc=baotoucloud,dc=com";
        Map<Object, Object> map = new HashMap<>();
        map.put("appId", appId);
        map.put("alg", alg);
        map.put("typ", typ);
        map.put("iss", iss);
        map.put("sub", sub);
        map.put("aud", aud);
        map.put("nbf", nbf);
        map.put("exp", exp);
        map.put("iat", iat);
        map.put("jti", jti);
        map.put("dn", dn);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
//        String url = "http://localhost:8083/api/jwt/AddJwt";
//        String url = "http://localhost:8083/api/jwt/DeleteJwt";
//        String url = "http://localhost:8083/api/jwt/SearchToken";
        String url = "http://localhost:8083/api/jwt/ResetJWT";
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println(a);
    }
}
