package api.handle.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public class QueryParameterException extends BasicSparkRequestException {

    public QueryParameterException(String name, String message) {
        super(400, buildMessage(name, message));
    }

    public QueryParameterException(String name) {
        super(400, buildMessage(name, "not define"));
    }

    private static String buildMessage(String name, String message) {
        return "Bad parameter[" + name + "],error message: " + message;
    }
}
