package api.manage;

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
 * @Date: Created in 18-9-26 下午3:24
 */

public class ManageOrganizationClient {

    public static void main(String[] args) throws IOException {
        String oldorganization = "zhengfu";
        String neworganization = "xinjiaojing";
        String dn = "dc=registry,dc=baotoucloud,dc=com";
        Map<Object, Object> map = new HashMap<>();
        map.put("oldorganization",oldorganization);
        map.put("neworganization",neworganization);
        map.put("dn",dn);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
//        String url = "http://localhost:8083/api/manage/AddO";
//        String url = "http://localhost:8083/api/manage/DeletO";
//        String url = "http://localhost:8083/api/manage/UpdataO";
        String url = "http://localhost:8083/api/manage/SearchO";
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println(a);
    }
}
