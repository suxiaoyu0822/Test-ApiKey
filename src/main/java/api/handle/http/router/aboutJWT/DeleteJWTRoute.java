package api.handle.http.router.aboutJWT;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.JWTEntity;
import api.handle.jwt.jwtldap.JwtLdap;
import api.handle.util.ReturnJson;
import com.google.inject.Inject;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-19 下午3:34
 */

public class DeleteJWTRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteJWTRoute.class);
    private JwtLdap jwtLdap;
    @Inject
    public DeleteJWTRoute(JwtLdap jwtLdap){
        this.jwtLdap=jwtLdap;
    }
    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        JWTEntity jwtEntity = bodyJsonEntity.getBodyJsonEntity(JWTEntity.class,request);
        String dn = jwtEntity.getDn();
        System.out.println("-----------------------------------删除JWT-----------------------------------");

        jwtLdap.connect();
        System.out.println("dn:"+dn);
        boolean b=jwtLdap.delete(dn);
        if (!b){
            System.out.println("删除jwt不存在，或者ldap异常，删除失败！");
            jwtLdap.close();
            String string = "删除jwt不存在，或者ldap异常，删除失败！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        System.out.println("删除jwt成功！");
        String string = "删除jwt成功！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        jwtLdap.close();
        return jsonObject;
    }
}
