package api.handle.http.json;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public interface Json {
    String toJson(Object object);
    String toJson(Object object, Type jsonType);

    <T> T fromJson(Class<T> type, String json) throws Exception;

    <T> List<T> fromJsonToList(Class<T> type, String json) throws Exception;
}
