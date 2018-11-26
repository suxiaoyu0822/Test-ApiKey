package api.handle.http.router.aboutJWT;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.JWTEntity;
import api.handle.jwt.JWTToken;
import api.handle.jwt.jwtldap.JwtLdap;
import api.handle.util.ReturnJson;
import com.google.inject.Inject;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-23 下午4:38
 */

public class ResetJWTRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResetJWTRoute.class);
    private JwtLdap jwtLdap;
    @Inject
    public ResetJWTRoute(JwtLdap jwtLdap){
        this.jwtLdap=jwtLdap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        JWTEntity jwtEntity = bodyJsonEntity.getBodyJsonEntity(JWTEntity.class,request);
        String dn = jwtEntity.getDn();
        System.out.println("-----------------------------------重置JWT-----------------------------------");
        jwtLdap.connect();
        System.out.println("dn:"+dn);
        //根据dn获取到生成token的信息
        Map<String,String> map = new LinkedHashMap();
        String appId = jwtEntity.getAppId();
        String searchFilter ="appId="+appId;
        String[] retAttribut = {"alg","typ","iss","sub","aud","exp","iat","jti"};
        List list = jwtLdap.FindJwtAttributes(dn,searchFilter,retAttribut);
        System.out.println(list);
        for (int i=0;i<list.size();i++){
//            System.out.println(list.get(i));
            String key = StringUtils.substringBeforeLast(String.valueOf(list.get(i)), ": ").replace("-","_");
            String value = StringUtils.substringAfterLast(String.valueOf(list.get(i)), ": ");
//                System.out.println(key+"+"+value);
//                System.out.println(list1.get(j));
            map.put(key, value);
        }
        String alg = map.get("alg");
        System.out.println("alg:"+alg);
        String typ = map.get("typ");
        System.out.println("typ:"+typ);
        String iss = map.get("iss");
        System.out.println("iss:"+iss);
        String sub = map.get("sub");
        System.out.println("sub:"+sub);
        String aud = map.get("aud");
        System.out.println("aud:"+aud);
        String nbf = map.get("iat");
        System.out.println("nbf:"+nbf);
        String exp = map.get("exp");
        System.out.println("exp:"+exp);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowdayTime = dateFormat.format(new Date());
        String iat =nowdayTime;
        System.out.println("iat:"+iat);
        String jti = map.get("jti");
        System.out.println("jti:"+jti);
        //生成token
        JWTToken jwtToken = new JWTToken();
        String token;
        try {
            token =jwtToken.createToken(alg,typ,iss,sub,aud,iat,nbf,exp,jti);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("[生成token失败,请重新操作！]");
            String string = "生成token失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        System.out.println("token:"+token);
        //修改ldap源jwt信息
        dn = "appId="+appId+","+dn;
        System.out.println("dn:"+dn);
        boolean b=jwtLdap.updateJwt(dn, iat, token);
        if (!b){
                System.out.println("修改JWT失败,请重新操作！");
                String string = "修改JWT失败,请重新操作！";
                JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
                return jsonObject;
            }
        System.out.println("重置jwt成功！");
        String string = "重置jwt成功！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        jwtLdap.close();
        return jsonObject;
    }
}
