package api.before;

import api.handle.util.MD5ObjectUtil;
import api.handle.util.NonceRandomUtil;
import net.sf.json.JSONArray;
import org.apache.commons.codec.binary.Base64;
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
 * @Date: Created in 18-9-11 上午9:33
 */

public class DigestClient {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://localhost:8083/api/user/Digest";
        String username = "sxy";
        String password = "123456";
        String realm = "1";
        MD5ObjectUtil md5ObjectUtil = new MD5ObjectUtil();
        String HA1 =md5ObjectUtil.encrypt(username+":"+realm+":"+password);
        System.out.println("HA1: "+HA1);
        String HA2 =md5ObjectUtil.encrypt("Post:"+url);
        System.out.println("HA2: "+HA2);
        NonceRandomUtil nonceRandomUtil = new NonceRandomUtil();
        String tokenStr =md5ObjectUtil.encrypt(HA1+":"+nonceRandomUtil.randomString(16)+":"+HA2);
        System.out.println("tokenStr: "+tokenStr);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setHeader("Authorization",tokenStr);
        String id = "1";
        String issuer= "sxy";
        String subject= "Test somthing";
        long ttlMillis=100000;
        Map<Object, Object> map1 = new HashMap<>();
        map1.put("Id", id);
        map1.put("Issuer", issuer);
        map1.put("Subject", subject);
        map1.put("TtlMillis", ttlMillis);
        JSONArray json = JSONArray.fromObject(map1);
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
