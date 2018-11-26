package api.handle.authority.acl;

import com.google.gson.annotations.SerializedName;

/**
 * @Description: ACL访问控制类
 * @Author:苏晓雨
 * @Date: Created in 18-10-19 上午10:27
 */

public class Acl {
    /**
     * 授权允许：  不确定   不允许   允许
     */
    public static final int ACL_NEUTRAL = -1;
    public static final int ACL_NO = 0;
    public static final int ACL_YES = 1;
    //用户和角色类型
    public static final String TYPE_USER = null;
    public static final String TYPE_ROLE = null;
    //ldap关键字
    @SerializedName("userid")
    String userid;
    @SerializedName("dn")
    String dn;
    @SerializedName("description")
    String description;
    @SerializedName("keyword")
    String keyword;
    @SerializedName("updt")
    String updt;
    @SerializedName("Attribut")
    String Attribut;

    public String getAttribut() {
        return Attribut;
    }

    public void setAttribut(String attribut) {
        Attribut = attribut;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUpdt() {
        return updt;
    }

    public void setUpdt(String updt) {
        this.updt = updt;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 授权状态，用其后四位（bit）来表示CRUD操作
     */
    private int aclState;

    /**
     * 表示是否继承，0表示不继承，1表示继承
     *
     */
    private int aclTriState;

    public int getAclState() {
        return aclState;
    }
    public void setAclState(int aclState) {
        this.aclState = aclState;
    }
    public int getAclTriState() {
        return aclTriState;
    }
    public void setAclTriState(int aclTriState) {
        this.aclTriState = aclTriState;
    }

    /**
     * acl实例跟主体和资源关联
     * 针对此实例进行授权，某种操作是否允许
     * @param permission 只可取0、1、2、3
     * @param yes true表示允许，false表示不允许
     */
    public void setPermission(int permission,boolean yes){
        int temp = 1;
        temp =temp <<permission;
        if (yes) {
            aclState |= temp;
        }else {
            aclState &= ~temp;
        }
    }

    /**
     * 获得ACL授权
     * @param permission C/R/U/D授权
     * @return 授权标识，允许/不允许/不确定
     */
    public int getPermission(int permission){

        //如果继承，则返回未定的授权信息
        if (aclTriState == 0xFFFFFFFF) {
            return ACL_NEUTRAL;
        }

        int temp = 1;
        temp = temp << permission;

        temp &=aclState;

        if (temp !=0) {
            return ACL_YES;
        }
        return ACL_NO;
    }

    /**
     * 设置本授权是否是继承的
     * @param yes true表示继承，false表示不继承
     */
    public void setExtends(boolean yes){
        //0xFFFFFFFF是-1的补码表示
        if (yes) {
            aclTriState =0xFFFFFFFF;
        }else {
            aclTriState = 0;
        }
    }
    //设置主体的类型
    public void setPrincipalType(String principalType) {
    }
    //设置主体的sn
    public void setPrincipalSn(int principalSn) {
    }
    //设置资源的sn
    public void setResourceSn(int resourceSn) {
    }
    //获取资源sn
    public Object getResourceSn() {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(0xFFFFFFFF);
    }
}
