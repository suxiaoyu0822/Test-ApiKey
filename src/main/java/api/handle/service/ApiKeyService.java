package api.handle.service;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-7 上午10:33
 */

public interface ApiKeyService {
    String getToken(String username,String password);
    String getApi_Key(String token);
}
