package api.handle.util;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-26 下午2:36
 */

public class ReturnJson {
    public static JSONObject ReturnFailJson(String str){
        Map<String,String> map = new HashMap<>();
        String key = "code";
        String value = "false";
        String message="content";
        map.put(key,value);
        map.put(message,str);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject;
    }
    public static JSONObject ReturnSuccessJson(String str){
        Map<String,String> map = new HashMap<>();
        String key = "code";
        String value = "true";
        String message="content";
        map.put(key,value);
        map.put(message,str);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject;
    }
}
