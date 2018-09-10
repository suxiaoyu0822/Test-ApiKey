package api;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-10 下午5:00
 */

public class UserRegisterClient {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://localhost:8083/api/user/Register";
        String username = "sxy";
        String password = "123456";
        byte[] tokenByte = Base64.encodeBase64((username+":"+password).getBytes());
        String tokenStr = new String(tokenByte);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setHeader("Authorization",tokenStr);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println(a);
    }
}
