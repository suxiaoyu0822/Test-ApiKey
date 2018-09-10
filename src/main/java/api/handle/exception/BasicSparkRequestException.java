package api.handle.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/9
 */
public class BasicSparkRequestException extends RuntimeException {
    private int code = 400;

    public BasicSparkRequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
