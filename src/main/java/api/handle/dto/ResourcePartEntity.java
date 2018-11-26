package api.handle.dto;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-31 下午6:13
 */

public class ResourcePartEntity {
    @SerializedName("security_id")
    String security_id;
    @SerializedName("resources_id")
    String resources_id;
    @SerializedName("rule_id")
    String rule_id;
    @SerializedName("client_ip_range")
    String client_ip_range = "0";
    @SerializedName("server_ip_range")
    String server_ip_range = "0";
    @SerializedName("start_time")
    String start_time = "0";
    @SerializedName("end_time")
    String end_time = "0";
    @SerializedName("create_time")
    String create_time;
    @SerializedName("user_name")
    String user_name = "0";
    @SerializedName("group_name")
    String group_name = "0";
    @SerializedName("action")
    String action = "drop";
    @SerializedName("dn")
    String dn;
    @SerializedName("newdn")
    String newdn;
    @SerializedName("oldorganizational")
    String oldorganizational;
    @SerializedName("newoldorganizational")
    String newoldorganizational;
    @SerializedName("security_name")
    String security_name;
    @SerializedName("resources_name")
    String resources_name;
    @SerializedName("updtkey")
    String updtkey;
    @SerializedName("updt")
    String updt;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    String nowdayTime = dateFormat.format(date);
    @SerializedName("now_time")
    String now_time = nowdayTime;
    @SerializedName("description")
    String description;
    @SerializedName("apiversionid")
    long apiversionid;

    public long getApiversionid() {
        return apiversionid;
    }

    public void setApiversionid(long apiversionid) {
        this.apiversionid = apiversionid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNow_time() {
        return now_time;
    }

    public void setNow_time(String now_time) {
        this.now_time = now_time;
    }

    public String getNewdn() {
        return newdn;
    }

    public void setNewdn(String newdn) {
        this.newdn = newdn;
    }

    public String getUpdtkey() {
        return updtkey;
    }

    public void setUpdtkey(String updtkey) {
        this.updtkey = updtkey;
    }

    public String getUpdt() {
        return updt;
    }

    public void setUpdt(String updt) {
        this.updt = updt;
    }

    public String getOldorganizational() {
        return oldorganizational;
    }

    public void setOldorganizational(String oldorganizational) {
        this.oldorganizational = oldorganizational;
    }

    public String getNewoldorganizational() {
        return newoldorganizational;
    }

    public void setNewoldorganizational(String newoldorganizational) {
        this.newoldorganizational = newoldorganizational;
    }

    public String getSecurity_name() {
        return security_name;
    }

    public void setSecurity_name(String security_name) {
        this.security_name = security_name;
    }

    public String getResources_name() {
        return resources_name;
    }

    public void setResources_name(String resources_name) {
        this.resources_name = resources_name;
    }

    public String getSecurity_id() {
        return security_id;
    }

    public void setSecurity_id(String security_id) {
        this.security_id = security_id;
    }

    public String getResources_id() {
        return resources_id;
    }

    public void setResources_id(String resources_id) {
        this.resources_id = resources_id;
    }

    public String getRule_id() {
        return rule_id;
    }

    public void setRule_id(String rule_id) {
        this.rule_id = rule_id;
    }

    public String getClient_ip_range() {
        return client_ip_range;
    }

    public void setClient_ip_range(String client_ip_range) {
        this.client_ip_range = client_ip_range;
    }

    public String getServer_ip_range() {
        return server_ip_range;
    }

    public void setServer_ip_range(String server_ip_range) {
        this.server_ip_range = server_ip_range;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

}
