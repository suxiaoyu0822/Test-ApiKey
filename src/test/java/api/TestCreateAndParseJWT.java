package api;

import api.handle.dto.ApiKey;
import api.handle.jwt.AboutJWT;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-7 下午1:54
 */

public class TestCreateAndParseJWT {
    public static void main(String[] args) {
        AboutJWT aboutJWT = new AboutJWT();
        ApiKey apiKey = new ApiKey();
        String id = "11";
        String issuer= "22";
        String subject= "33";
        long ttlMillis=10000;
        apiKey.setSecret("1122");
        String s =aboutJWT.createJWT(id,issuer,subject,ttlMillis,apiKey);
        System.out.println(s);
        aboutJWT.parseJWT(s,apiKey);

    }
}
