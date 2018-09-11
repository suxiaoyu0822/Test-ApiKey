package api.handle.service.impl;

import api.handle.service.ApiKeyService;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Base64;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-7 上午10:33
 */

public class ApiKeyServiceImpl implements ApiKeyService {
    @Override
    public String getToken(String ApiKey) {
        byte[] credentials = ApiKey.getBytes();
        return Base64.getEncoder().encodeToString(credentials);
    }

    @Override
    public String getApi_Key(String token) {
        if (!token.isEmpty()){
                return RandomStringUtils.randomAlphanumeric(16);
        }
        return null;
    }
}
