package api.handle.dto;

import api.handle.exception.QueryParameterException;
import api.handle.http.json.DefaultGsonJson;
import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import spark.Request;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-7 下午3:20
 */

public class BodyJsonEntity {

    public <T> T getBodyJsonEntity(Class<T> classType, Request request) throws Exception {
        DefaultGsonJson defaultGsonJson = new DefaultGsonJson();
        String s = request.body();
        String body = StringUtils.strip(s,"[]");
        T entity = defaultGsonJson.fromJson(classType, body);
        if (entity == null) {
            System.out.println("json format error");
        }
        return entity;
    }
    public String getQueryParameter(Request request, String name, boolean require) throws QueryParameterException {
        String parameter = request.queryParams(name);
        if (require)
            if (Strings.isNullOrEmpty(request.queryParams(name))) {
                throw new QueryParameterException(name);
            }
        return parameter == null ? "" : parameter;
    }

    public String getHeaders(Request request, String name, boolean require) {
        String header = request.headers(name);
        if (require)
            if (Strings.isNullOrEmpty(request.headers(name))) {
                System.out.println("Exception+++++"+name);
            }
        return header == null ? "" : header;
    }

}
