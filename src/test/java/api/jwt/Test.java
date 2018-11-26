package api.jwt;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.StringUtils;


/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-15 上午10:06
 */

public class Test {
    /**
     * APP登录Token的生成和解析
     *
     */

    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
    public static final String SECRET = "JKKLJOoasdlfj";
    /** token 过期时间: 10天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    /**
     * JWT生成Token.<br/>
     *
     * JWT构成: header, payload, signature
     *
     * @param user_id
     *            登录成功后用户user_id, 参数user_id不可传空
     */
    public static String createToken(Long user_id) throws Exception {
        //签发时间
        Date iatDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(iatDate);
        c.add(Calendar.DAY_OF_MONTH, 1);    //+1天
        //什么时间之前不可用
        Date beforDate = c.getTime();
        // 过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();
        System.out.println(expiresDate);

        //头部
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        //创建token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("#……", "哦2哦哦") // payload 自定义数据
                .withClaim("user_id", null == user_id ? null : user_id.toString())
                .withIssuer("suxiaoyu")             //签发者
                .withSubject("all people")          //面向的用户
                .withAudience("Receive people")     //接收者
                .withIssuedAt(iatDate)              //签发时间
//                .withNotBefore(beforDate)           //什么时间之前不可用
                .withExpiresAt(expiresDate)         //过期时间
                .withJWTId("qwer")                  //jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
                .sign(Algorithm.HMAC256(SECRET));   //尾部生成token签名

        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
             e.printStackTrace();
//             token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static Long getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("user_id");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(user_id_claim.asString());
    }
    public static void main(String[] args) throws Exception {
        Test test = new Test();
        long user_id = 123456;
        String token = test.createToken(user_id);
        System.out.println(token);
//        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbGwgcGVvcGxlIiwiYXVkIjoiUmVjZWl2ZSBwZW9wbGUiLCJ1c2VyX2lkIjoiMTIzNDU2IiwiaXNzIjoic3V4aWFveXUiLCJleHAiOjE1NDMxMTQ1NjMsImlhdCI6MTU0MjI1MDU2MywianRpIjoicXdlciJ9.2De9QgbLpEPAbRlMhUC87qyWzJerozH4vEn3ngcNlnw";
        Map<String, Claim> map = test.verifyToken(token);
        System.out.println(map);
//        System.out.println(map.get("sub").asString());
//        System.out.println(map.get("aud").asString());
//        System.out.println(map.get("user_id").asString());
//        System.out.println(map.get("iss").asString());
        System.out.println(map.get("iat").asDate());
        System.out.println(map.get("exp").asDate());

//        System.out.println(map.get("jti").asString());
        System.out.println(map.get("#……").asString());
    }

}
