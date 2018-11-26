package api.handle.dto;

import com.google.gson.annotations.SerializedName;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-7 下午2:27
 */

public class UserEntity {
    @SerializedName("cn")
    private String cn;
    @SerializedName("userPassword")
    private String userPassword;
    @SerializedName("telephoneNumber")
    private String telephoneNumber;
    @SerializedName("organization")
    private String organization;
    @SerializedName("organizationalUnit")
    private String organizationalUnit;
    @SerializedName("mail")
    private String mail;
    @SerializedName("description")
    private String description;
    @SerializedName("clientNonce")
    private String clientNonce;
    @SerializedName("uri")
    private String uri;
    @SerializedName("reaml")
    private String reaml;
    @SerializedName("keyword")
    private String keyword;
    @SerializedName("newinfo")
    private String newinfo;
    @SerializedName("uid")
    private String uid;
    @SerializedName("dn")
    private String dn;
    @SerializedName("newdn")
    private String newdn;
    @SerializedName("givenName")
    private String givenName;
    @SerializedName("employeeType")
    private String employeeType;
    @SerializedName("initials")
    private String initials;
    @SerializedName("postalAddress")
    private String postalAddress;

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNewdn() {
        return newdn;
    }

    public void setNewdn(String newdn) {
        this.newdn = newdn;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewinfo() {
        return newinfo;
    }

    public void setNewinfo(String newinfo) {
        this.newinfo = newinfo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
