package api.handle.dto;

import com.google.gson.annotations.SerializedName;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-7 下午2:27
 */

public class UserEntity {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
