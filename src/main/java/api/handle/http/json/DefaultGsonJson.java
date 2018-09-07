package api.handle.http.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public class DefaultGsonJson implements Json {
    private Gson gson;

    @Inject
    public DefaultGsonJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = builder.create();
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    public String toJson(Object object, Type jsonType) {
        return gson.toJson(object, jsonType);
    }

    @Override
    public <T> T fromJson(Class<T> type, String json) throws Exception {
        return gson.fromJson(json, type);
    }

    @Override
    public <T> List<T> fromJsonToList(Class<T> type, String json) throws Exception {
        Type listType = new TypeToken<List<T>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }
}
