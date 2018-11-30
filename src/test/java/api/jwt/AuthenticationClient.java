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
 * @Date: Created in 18-11-27 上午10:49
 */

public class AuthenticationClient {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
//        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiLoi4_lpKfkuo4iLCJhdWQiOiLoi4_mmrTpm6giLCJuYmYiOjE1NzQxNzkyMDAsImlzcyI6IuiLj-S4i-mbqCIsImV4cCI6MTU3NTE4ODk4MywiaWF0IjoxNTQzNTA3MjAwLCJqdGkiOiJxd2VyIn0.L4cIR_8yyJZaVsENRKwMINP3SFnAv5uaTButHOwuzXU";
//        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiLoi4_lpKfkuo4iLCJhdWQiOiLoi4_mmrTpm6giLCJuYmYiOjE1NDM0MjA4MDAsImlzcyI6IuiLj-S4i-mbqCIsImV4cCI6MTU1MjExOTUyNiwiaWF0IjoxNTQzNDIwODAwLCJqdGkiOiJxd2VyIn0.II_YYw12QWwidQay-XGWzugMzWuggTwAcNEqnh9f8i4";
        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwIiwiYXVkIjoiMCIsIm5iZiI6MTU0MzQyMDgwMCwiaXNzIjoi5ZGm5ZGm5ZOfIiwiZXhwIjoxNTQzNTcxNzU3LCJpYXQiOjE1NDI5MDI0MDAsImp0aSI6IjAifQ.0_i2LtNrItDbgdZWBO8VOcAMZJ6xPYeuxa1dAzbtk-w";
//        String token ="eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJzdWIiOiLoi4_lpKfkuo4iLCJhdWQiOiLoi4_mmrTpm6giLCJuYmYiOjE1NDMyNDgwMDAsImlzcyI6IuiLj-S4i-mbqCIsImV4cCI6MTU0MzM3MDExMCwiaWF0IjoxNTQzMjQ4MDAwLCJqdGkiOiJxd2VyIn0.D5OcYtQQ9LUOsJBzhbgFlTR1tPzRI1tWEfeyHoP_YhNz9xrZoVU1W0TjCXDrksox";
        String url = "http://localhost:8083/api/jwt/Authentication";
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setHeader("Authorization",token);
//        String appId = "211";     //未在可用期内
        String appId = "212";   //正常
//        String appId = "3";      //正常
//        String appId = "121";    //过期
        Map<Object, Object> map = new HashMap<>();
        map.put("appId",appId);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println(a);
    }
}
