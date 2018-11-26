package api.before;

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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-3 上午11:39
 */

public class PostTest {
    public static void main(String[] args) {
        Executors.newScheduledThreadPool(1)
                .scheduleWithFixedDelay(() -> {
                    String message1 = "sxy";
                    String message2 = "123456";
                    Map<Object, Object> map1 = new HashMap<>();
                    map1.put("username", message1);
                    map1.put("password", message2);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    String url = "http://localhost:8083/api/key/do";
                    HttpPost httppost = new HttpPost(url);
                    JSONArray json = JSONArray.fromObject(map1);
                    StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
                    entity.setContentEncoding("UTF-8");
                    entity.setContentType("application/json");
                    httppost.setEntity(entity);
                    System.out.println("executing request " + httppost.getURI());
                    try {
                        CloseableHttpResponse response = httpclient.execute(httppost);
                        HttpEntity resEntity = response.getEntity();
                        String a = EntityUtils.toString(resEntity, "UTF-8");
                        System.out.println(a);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, 0, 1,   TimeUnit.SECONDS);

    }
}
