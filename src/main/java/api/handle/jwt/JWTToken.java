package api.handle.jwt;

import api.handle.util.PropertyUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-15 下午5:08
 */

public class JWTToken {
    Properties properties = PropertyUtil.loadProperties("ldap.properties");
    public final String SECRET = properties.getProperty("SECRET");
    public final int calendarField = Calendar.DATE;
    RSAPublicKey publicKey =null;
    RSAPrivateKey privateKey =null;
    public String createToken(String alg,String typ,String iss,String sub,String aud,String iat,String nbf,String exp,String jti) throws Exception {
        //签发时间
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
        Date iatDate = sdf.parse(iat);
//        Date iatDate = new Date();
        //什么时间之前不可用
//        Calendar c = Calendar.getInstance();
//        c.setTime(iatDate);
//        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(nbf));    //+nbf天
//        Date beforDate = c.getTime();
        Date beforDate = sdf.parse(nbf);
        //过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, Integer.parseInt(exp));
        Date expiresDate = nowTime.getTime();
        //头部
        Map<String, Object> map = new HashMap<>();
        map.put("alg", alg);
        map.put("typ", typ);
        String token = JWT.create().withHeader(map) // header
                .withIssuer(iss)                    //签发者
                .withSubject(sub)                   //面向的用户
                .withAudience(aud)                  //接收者
                .withIssuedAt(iatDate)              //签发时间
                .withNotBefore(beforDate)           //什么时间之前不可用（大于等于签发时间）
                .withExpiresAt(expiresDate)         //过期时间
                .withJWTId(jti)                     //jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
                .sign(SelectSecret(alg));           //尾部生成token签名

        return token;
    }

    public Map<String, Claim> verifyToken(String token,String alg) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(SelectSecret(alg)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("Exception:"+e);
//             token 校验失败, 抛出Token验证非法异常

        }
        return jwt.getClaims();
    }

    public int ReturnverifyToken(String token,String alg) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(SelectSecret(alg)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Exception:"+e);
            if(e.getMessage().contains("The Token has expired on")){
//                System.out.println("此token已过期！");
                return 1;
            }else if (e.getMessage().contains("The Token can't be used before")){
//                System.out.println("此token在不可用时间段内！");
                return 2;
            }else {
//                System.out.println("解析算法错误！");
                return 3;
            }
//             token 校验失败, 抛出Token验证非法异常

        }
        return 0;
    }

    public Algorithm SelectSecret(String alg){
        Algorithm algorithm = null;
        if (alg.equals("ES512")){
//            algorithm = Algorithm.ECDSA512(SECRET);
        }else if (alg.equals("HS384")){
            algorithm = Algorithm.HMAC384(SECRET);
        }else if (alg.equals("HS512")){
            algorithm = Algorithm.HMAC512(SECRET);
        }else if (alg.equals("RS256")){
            algorithm = Algorithm.RSA256(publicKey,privateKey);
        }else if (alg.equals("RS384")){
            algorithm = Algorithm.RSA384(publicKey,privateKey);
        }else if (alg.equals("RS512")){
            algorithm = Algorithm.RSA512(publicKey,privateKey);
        }else if (alg.equals("ES256")){
//            algorithm = Algorithm.ECDSA256(SECRET);
        }else if (alg.equals("ES384")){
//            algorithm = Algorithm.ECDSA384(SECRET);
        }else {
            algorithm = Algorithm.HMAC256(SECRET);
        }
        return algorithm;
    }
    public static void main(String[] args) throws Exception {
//        Date date = new Date();
//        System.out.println("date:"+date);
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String nowdayTime = dateFormat.format(date);
//        System.out.println("nowdayTime："+nowdayTime);
//        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date1 = sdf.parse(nowdayTime);
//        System.out.println("date1:"+date1);

        JWTToken jwtToken = new JWTToken();
        String alg = "HS256";
//        String alg = "HS384";
//        String alg = "HS512";
//        String typ = "JWT";
//        String iss = "sxy";
//        String sub = "yxs";
//        String aud = "xys";
//        int nbf = 0;
//        int exp = 10;
//        String jti = "123456789";
//        String token = jwtToken.createToken(alg,typ,iss,sub,aud,nbf,exp,jti);
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwIiwiYXVkIjoiMCIsIm5iZiI6MTU0MzQyMDgwMCwiaXNzIjoi5ZGm5ZGm5ZOfIiwiZXhwIjoxNTQzNTcxNzU3LCJpYXQiOjE1NDI5MDI0MDAsImp0aSI6IjAifQ.0_i2LtNrItDbgdZWBO8VOcAMZJ6xPYeuxa1dAzbtk-w";
        System.out.println("token:"+token);
        Map<String, Claim> map = jwtToken.verifyToken(token,alg);
        System.out.println(map);
//        System.out.println(map.get("iss").asString());
//        System.out.println(map.get("sub").asString());
//        System.out.println(map.get("aud").asString());
//        System.out.println(map.get("iat").asDate());
//        System.out.println(map.get("nbf").asDate());
//        System.out.println(map.get("exp").asDate());
//        System.out.println(map.get("jti").asString());
    }
}
