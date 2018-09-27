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
 * @Date: Created in 18-9-27 上午9:21
 */

public class ManageOrganizationalUnitClient {
    public static void main(String[] args) throws IOException {
        //创建-创建组织单元、删除-删除组织单元、修改-修改组织单元-其他-操作失败
        String handle = "创建";
        String oldorganization = "jiaojing";
        String oldorganizationalUnit="zhiqinzu";
        String neworganizationalUnit="xunluozu";
        Map<Object, Object> map = new HashMap<>();
        map.put("handle",handle);
        map.put("oldorganization",oldorganization);
        map.put("oldorganizationalUnit",oldorganizationalUnit);
        map.put("neworganizationalUnit",neworganizationalUnit);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://localhost:8083/api/manage/organizationalUnit";
        HttpPost httppost = new HttpPost(url);
        GetDigestUtil getDigestUtil = new GetDigestUtil();
        httppost.setHeader("Content-type", "application/json; charset=utf-8");
        httppost.setEntity(entity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        String a = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println(a);
    }
}
