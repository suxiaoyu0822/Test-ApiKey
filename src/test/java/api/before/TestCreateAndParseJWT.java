package api.before;

import api.handle.dto.ApiKey;
import api.handle.jwt.AboutJWT;
import org.apache.commons.codec.binary.Base64;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-7 下午1:54
 */

public class TestCreateAndParseJWT {
    public static void main(String[] args) {
//        AboutJWT aboutJWT = new AboutJWT();
//        ApiKey apiKey = new ApiKey();
//        String id = "11";
//        String issuer= "22";
//        String subject= "33";
//        long ttlMillis=10000;
//        apiKey.setSecret("1122");
//        String s =aboutJWT.createJWT(id,issuer,subject,ttlMillis,apiKey);
//        System.out.println(s);
//        aboutJWT.parseJWT(s,apiKey);
        String username = "sxy";
        String password = "123456";
        byte[] tokenByte = Base64.encodeBase64((username+":"+password).getBytes());
        String tokenStr = new String(tokenByte);
        System.out.println(tokenStr);
        String s = new String(Base64.decodeBase64(tokenStr.getBytes()));
        System.out.println(s);
    }
}
