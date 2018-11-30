package api.handle.http.router.aboutJWT;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.JWTEntity;
import api.handle.dto.ResourcePartEntity;
import api.handle.http.router.authorityRouter.AuthorityUpdateRuleRoute;
import api.handle.jwt.JWTToken;
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
 * @Date: Created in 18-11-19 下午3:31
 */

public class AddJWTRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddJWTRoute.class);
    private JwtLdap jwtLdap;
    @Inject
    public AddJWTRoute(JwtLdap jwtLdap){
        this.jwtLdap=jwtLdap;
    }
    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        JWTEntity jwtEntity = bodyJsonEntity.getBodyJsonEntity(JWTEntity.class,request);
        String appId = jwtEntity.getAppId();
        System.out.println("appId:"+appId);
        String alg = jwtEntity.getAlg();
        System.out.println("alg:"+alg);
        String typ = jwtEntity.getTyp();
        System.out.println("typ:"+typ);
        String iss = jwtEntity.getIss();
        System.out.println("iss:"+iss);
        String sub = jwtEntity.getSub();
        System.out.println("sub:"+sub);
        String aud = jwtEntity.getAud();
        System.out.println("aud:"+aud);
        String nbf = jwtEntity.getNbf();
        System.out.println("nbf:"+nbf);
        String exp = jwtEntity.getExp();
        System.out.println("exp:"+exp);
        String iat = jwtEntity.getIat();
        System.out.println("iat:"+iat);
        String jti = jwtEntity.getJti();
        System.out.println("jti:"+jti);
        System.out.println("-----------------------------------添加jwt-----------------------------------");
        jwtLdap.connect();
        //生成token
        JWTToken jwtToken = new JWTToken();
        String token;
        try {
            token =jwtToken.createToken(alg,typ,iss,sub,aud,iat,nbf,exp,jti);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("[生成token失败,请重新操作！]");
            jwtLdap.close();
            String string = "生成token失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        jwtEntity.setToken(token);
        //存储到ldap
        try {
            jwtLdap.saveToken(jwtEntity);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("[所添加的jwt已存在，存储失败！]");
            String string = "所添加的jwt已存在或ldap服务器异常，存储失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        jwtLdap.close();
        String string = "成功添加jwt";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        return jsonObject;
    }
}
