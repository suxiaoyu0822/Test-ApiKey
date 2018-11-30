package api.manage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
        String oldorganization = "公安";
        String oldorganizationalUnit="通信处";
        String neworganization = "jiaojing";
        String neworganizationalUnit = "zhiqinzu";
        String keyword = "cn";
        String newinfo = "通信处处长";
//        String username = "局长";
//        String cn = "苏晓雨";
//        String cn = "吕献军";
        String cn = "金鹏程";
        String userPassword = "123456";
        String givenName = "通信一组";
        String employeeType = "管理员";
//        char[] e = {'a','b'};
//        String employeeType = new String(e);
        String telephoneNumber = "13122223333";
        String mail = "123456789@yahoo.com";
        String initials = "empty";
//        String initials = "Quote";
//        String initials = "BeQuote";
        String postalAddress="cn=苏晓雨,ou=科技一组,ou=科技处,o=公安,dc=registry,dc=baotoucloud,dc=com";
//        String dn = "ou=运维二组,ou=运维组,o=管理组,dc=registry,dc=baotoucloud,dc=com";
//        String dn = "ou=科技一组,ou=科技处,o=厂商,dc=registry,dc=baotoucloud,dc=com";
        String dn = "ou=组织统战部1,o=政府,dc=registry,dc=baotoucloud,dc=com";
        String description = "我是备注";
        Map<Object, Object> map = new HashMap<>();
        map.put("cn",cn);
        map.put("userPassword",userPassword);
        map.put("givenName",givenName);
        map.put("employeeType",employeeType);
        map.put("telephoneNumber",telephoneNumber);
        map.put("oldorganization",oldorganization);
        map.put("oldorganizationalUnit",oldorganizationalUnit);
        map.put("neworganization",neworganization);
        map.put("neworganizationalUnit",neworganizationalUnit);
        map.put("newinfo",newinfo);
        map.put("keyword",keyword);
        map.put("mail",mail);
        map.put("initials",initials);
        map.put("postalAddress",postalAddress);
        map.put("description",description);
        map.put("dn",dn);
        JSONArray json = JSONArray.fromObject(map);
        StringEntity entity = new StringEntity(json.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient httpclient = HttpClients.createDefault();
//        String url = "http://localhost:8083/api/manage/AddUser";
//        String url = "http://localhost:8083/api/manage/DeletUser";
//        String url = "http://localhost:8083/api/manage/UpdataUser";
        String url = "http://localhost:8083/api/manage/SearchUser";
//        String url = "http://192.168.101.151:8083/api/manage/SearchUser";
//        String url = "http://localhost:8083/api/manage/MoveUser";
//        String url = "http://localhost:8083/api/manage/QuoteUser";
//        String url = "http://localhost:8083/api/manage/MoveUpdata";
//        String url = "http://localhost:8083/api/manage/SearchAll";
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
