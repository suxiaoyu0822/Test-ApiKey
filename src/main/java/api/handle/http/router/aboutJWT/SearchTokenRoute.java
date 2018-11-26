package api.handle.http.router.aboutJWT;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.JWTEntity;
import api.handle.jwt.jwtldap.JwtLdap;
import api.handle.util.ReturnJson;
import com.google.inject.Inject;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-22 下午3:50
 */

public class SearchTokenRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTokenRoute.class);
    private JwtLdap jwtLdap;
    @Inject
    public SearchTokenRoute(JwtLdap jwtLdap){
        this.jwtLdap=jwtLdap;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        JWTEntity jwtEntity = bodyJsonEntity.getBodyJsonEntity(JWTEntity.class,request);
        String dn = jwtEntity.getDn();
        String appId = jwtEntity.getAppId();
        System.out.println("-----------------------------------查询token-----------------------------------");
        System.out.println("dn:"+dn);
        System.out.println("appId:"+appId);
        String searchFilter ="appId="+appId;
        String retAttrs = "token";
        jwtLdap.connect();
        String token;
        try{
            token = jwtLdap.find(dn,searchFilter,retAttrs);
            System.out.println("token:"+token);
        }catch (Exception e){
            System.out.println("查询的appId不存在，或者ldap异常，查询失败！");
            String string = "查询的appId不存在，或者ldap异常，查询失败！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        System.out.println("查询token成功！");
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(token);
        jwtLdap.close();
        return jsonObject;
    }
}
