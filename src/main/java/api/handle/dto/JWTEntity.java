package api.handle.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-19 下午5:27
 */

public class JWTEntity {
    @SerializedName("alg")
    String alg;
    @SerializedName("typ")
    String typ;
    @SerializedName("iss")
    String iss;
    @SerializedName("sub")
    String sub = "0";
    @SerializedName("aud")
    String aud = "0";
    @SerializedName("exp")
    String exp;
    @SerializedName("nbf")
    String nbf = "0";
    @SerializedName("iat")
    String iat;
    @SerializedName("jti")
    String jti = "0";
    @SerializedName("dn")
    String dn;
    @SerializedName("appId")
    String appId;
    @SerializedName("token")
    String token;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getNbf() {
        return nbf;
    }

    public void setNbf(String nbf) {
        this.nbf = nbf;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
