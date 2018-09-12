package api.handle.util;

import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-12 下午5:57
 */

public class GetDigestUtil {
    public String GetResponse(Map map){
        MD5ObjectUtil md5ObjectUtil = new MD5ObjectUtil();
        String HA1=md5ObjectUtil.encrypt(map.get("username")+":"+map.get("reaml")+":"+map.get("password"));
        String HA2=md5ObjectUtil.encrypt("post:"+map.get("uri"));
        String response=md5ObjectUtil.encrypt(HA1+":"+map.get("clientNonce")+HA2);
        return response;
    }
}
