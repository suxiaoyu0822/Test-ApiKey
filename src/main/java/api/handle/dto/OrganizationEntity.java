package api.handle.dto;

import com.google.gson.annotations.SerializedName;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-26 下午4:04
 */

public class OrganizationEntity {
    @SerializedName("handle")
    private String handle;
    @SerializedName("oldorganization")
    private String oldorganization;
    @SerializedName("neworganization")
    private String neworganization;
    @SerializedName("oldorganizationalUnit")
    private String oldorganizationalUnit;
    @SerializedName("neworganizationalUnit")
    private String neworganizationalUnit;

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getOldorganization() {
        return oldorganization;
    }

    public void setOldorganization(String oldorganization) {
        this.oldorganization = oldorganization;
    }


    public String getNeworganization() {
        return neworganization;
    }

    public void setNeworganization(String neworganization) {
        this.neworganization = neworganization;
    }

    public String getOldorganizationalUnit() {
        return oldorganizationalUnit;
    }

    public void setOldorganizationalUnit(String oldorganizationalUnit) {
        this.oldorganizationalUnit = oldorganizationalUnit;
    }

    public String getNeworganizationalUnit() {
        return neworganizationalUnit;
    }

    public void setNeworganizationalUnit(String neworganizationalUnit) {
        this.neworganizationalUnit = neworganizationalUnit;
    }
}
