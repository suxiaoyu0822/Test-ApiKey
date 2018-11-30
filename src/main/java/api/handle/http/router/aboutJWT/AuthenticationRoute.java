package api.handle.http.router.aboutJWT;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.JWTEntity;
import api.handle.jwt.JWTToken;
import api.handle.jwt.jwtldap.JwtLdap;
import api.handle.util.ReturnJson;
import com.auth0.jwt.interfaces.Claim;
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
 * @Date: Created in 18-11-26 下午5:09
 */

public class AuthenticationRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRoute.class);
    private JwtLdap jwtLdap;
    @Inject
    public AuthenticationRoute(JwtLdap jwtLdap){
        this.jwtLdap=jwtLdap;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {

        //获取api网关传送过来的信息(token，appId)
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        String token =bodyJsonEntity.getHeaders(request,"Authorization",false);
        System.out.println("-----------------------------------jwt认证-----------------------------------");
        jwtLdap.connect();
        System.out.println("token:"+token);
        JWTEntity jwtEntity = bodyJsonEntity.getBodyJsonEntity(JWTEntity.class,request);
        String appId = jwtEntity.getAppId();
        System.out.println("appId:"+appId);
        //根据用户id查询ldap库内用户信息
        String dn ="o=应用,dc=resources,dc=baotoucloud,dc=com";
        String searchFilter = "appId="+appId;
        String[] Attribut = {"alg","iss","sub","aud","iat","nbf","exp","jti"};
        List list = jwtLdap.FindJwtAttributes(dn,searchFilter,Attribut);
//        System.out.println("list:"+list);
        Map<String,String> map = new LinkedHashMap<>();
        for (int i=0;i<list.size();i++){
            String key = StringUtils.substringBeforeLast(String.valueOf(list.get(i)), ": ");
            String value = StringUtils.substringAfterLast(String.valueOf(list.get(i)), ": ");
//                System.out.println(key+"+"+value);
            map.put(key,value);
        }
//        System.out.println("map:"+map);
        String alg =map.get("alg");
        System.out.println("alg:"+alg);
        String iss =map.get("iss");
        System.out.println("iss:"+iss);
        String sub =map.get("sub");
        System.out.println("sub:"+sub);
        String aud =map.get("aud");
        System.out.println("aud:"+aud);
        String iat =map.get("iat");
        System.out.println("iat:"+iat);
        String nbf =map.get("nbf");
        System.out.println("nbf:"+nbf);
        String exp =map.get("exp");
        System.out.println("exp:"+exp);
        String jti =map.get("jti");
        System.out.println("jti:"+jti);
        //将用户信息（将传送的token进行解析，判断是否过期等提取用户信息）和ldap库内信息进行比较
        JWTToken jwtToken = new JWTToken();
        int mark = jwtToken.ReturnverifyToken(token,alg);
//        while (mark==0){
//           System.out.println("此token正常！");
//        }
        while (mark==1){
            System.out.println("此token已过期！");
            String str = "此token已过期";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(str);
            return jsonObject;
        }
        while (mark==2){
            System.out.println("此token不在可用时间内！");
            String str = "此token不在可用时间内";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(str);
            return jsonObject;
        }
        while (mark==3){
            System.out.println("此token和应用的解析算法不匹配！");
            String str = "此token和应用的解析算法不匹配";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(str);
            return jsonObject;
        }
        Map<String, Claim> tokenmap = jwtToken.verifyToken(token,alg);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tokeniss = tokenmap.get("iss").asString();
        System.out.println("tokeniss:"+tokeniss);
        String tokensub = tokenmap.get("sub").asString();
        System.out.println("tokensub:"+tokensub);
        String tokenaud = tokenmap.get("aud").asString();
        System.out.println("tokenaud:"+tokenaud);
        Date dateiat = tokenmap.get("iat").asDate();
        String tokeniat = dateFormat.format(dateiat);
        System.out.println("tokeniat:"+tokeniat);
        Date datenbf = tokenmap.get("nbf").asDate();
        String tokennbf = dateFormat.format(datenbf);
        System.out.println("tokennbf:"+tokennbf);
        Date dateexp = tokenmap.get("exp").asDate();
        String tokenexp = String.valueOf((dateexp.getTime()-dateiat.getTime())/(1000*60*60*24));
        System.out.println("tokenexp:"+tokenexp);
        String tokenjti = tokenmap.get("jti").asString();
        System.out.println("tokenjti:"+tokenjti);
        //返回结果
        if (iss.equals(tokeniss)&&sub.equals(tokensub)&&aud.equals(tokenaud)&&iat.equals(tokeniat)&&nbf.equals(tokennbf)&&exp.equals(tokenexp)&&jti.equals(tokenjti)){
            System.out.println("JWT认证成功！");
            jwtLdap.close();
            JSONObject jsonObject = ReturnJson.ReturnSuccessJson("null");
            return jsonObject;
        }
        System.out.println("此token组成和应用绑定的信息不相符，认证失败！");
        String str = "此token组成和应用绑定的信息不相符";
        jwtLdap.close();
        JSONObject jsonObject = ReturnJson.ReturnFailJson(str);
        return jsonObject;
    }
}
