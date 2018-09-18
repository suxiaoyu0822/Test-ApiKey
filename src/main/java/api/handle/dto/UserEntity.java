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
    @SerializedName("man")
    private String man;
    @SerializedName("address")
    private String address;
    @SerializedName("organization")
    private String organization;
    @SerializedName("email")
    private String email;
    @SerializedName("clientNonce")
    private String clientNonce;
    @SerializedName("uri")
    private String uri;
    @SerializedName("reaml")
    private String reaml;
    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


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

    public String getClientNonce() {
        return clientNonce;
    }

    public void setClientNonce(String clientNonce) {
        this.clientNonce = clientNonce;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getReaml() {
        return reaml;
    }

    public void setReaml(String reaml) {
        this.reaml = reaml;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
