package api.handle.jwt;


import api.handle.dto.ApiKey;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-7 上午11:56
 */

public class AboutJWT {
    public String createJWT(String id, String issuer, String subject, long ttlMillis, ApiKey apiKey) {

//The JWT signature algorithm we will be using to sign the token
        //JWT签名算法，我们将用它来签署令牌
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

//We will sign our JWT with our ApiKey secret
        //我们将用我们的ApiKey秘密签署我们的JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        //让我们设置JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

//if it has been specified, let's add the expiration
        //如果已经指定了，让我们添加过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

//Builds the JWT and serializes it to a compact, URL-safe string
        //构建JWT并将其序列化为紧凑的URL安全字符串
        return builder.compact();
    }




    //Sample method to validate and read the JWT
    //验证和读取JWT的示例方法
    public void parseJWT(String jwt,ApiKey apiKey) {
    //This line will throw an exception if it is not a signed JWS (as expected)
        //如果不是JWS（如预期），则该行将引发异常。
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(apiKey.getSecret()))
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }


}
