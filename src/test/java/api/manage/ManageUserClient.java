package api.manage;

import api.handle.util.GetDigestUtil;
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

public class ManageUserClient {
    public static void main(String[] args) throws IOException {
        //创建-创建用户、删除-删除用户、修改-修改用户、移动-移动用户、引用-引用用户到其他组
        String handle = "引用";
        String oldorganization = "gongan";
        String oldorganizationalUnit="yanfabu";
        String neworganization = "jiaojing";
        String neworganizationalUnit = "zhiqinzu";
        String keyword = "sn";
        String newinfo = "ggggggg";
        String username = "jpc";
        String password = "123456";
        String address = "baotou";
        String telephoneNumber = "123456789";
        String company = "anyun";
        String email = "123456789@yahoo.com";
        Map<Object, Object> map = new HashMap<>();
        map.put("handle",handle);
        map.put("username",username);
        map.put("password",password);
        map.put("address",address);
        map.put("telephoneNumber",telephoneNumber);
        map.put("oldorganization",oldorganization);
        map.put("oldorganizationalUnit",oldorganizationalUnit);
        map.put("neworganization",neworganization);
        map.put("neworganizationalUnit",neworganizationalUnit);
        map.put("newinfo",newinfo);
        map.put("keyword",keyword);
        map.put("company",company);
        map.put("email",email);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://localhost:8083/api/manage/user";
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println(a);
    }


}
