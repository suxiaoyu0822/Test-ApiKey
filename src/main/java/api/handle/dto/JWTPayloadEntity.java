package api.handle.dto;

import com.google.gson.annotations.SerializedName;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-10 下午6:03
 */

public class JWTPayloadEntity {
    @SerializedName("Id")
    private String id;
    @SerializedName("Issuer")
    private String issuer;
    @SerializedName("Subject")
    private String subject;
    @SerializedName("TtlMillis")
    private long ttlMillis;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getTtlMillis() {
        return ttlMillis;
    }

    public void setTtlMillis(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }
}
