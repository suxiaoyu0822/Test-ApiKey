package api.handle.authority.permission;

import api.handle.authority.acl.Acl;
import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.authority.resourcesldap.impl.ResourceLdapImpl;

import javax.naming.NamingException;
import java.util.*;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-19 上午10:24
 */

public class Permission {
    private static final int READ = 0;

    /**
     * 授权
     * @param principalType 主体类型
     * @param principalSn 主体标识
     * @param resourceSn 资源标识
     * @param permission 权限：C/R/U/D
     * @param yes 是否允许，true表示允许；false表示不允许
     */
    public void addOrUpdatePermission(String principalType, int principalSn,
                                      int resourceSn, int permission, boolean yes) throws NamingException {

        //根据主体标识和资源标识查找ACL实例
        Acl acl = findACL(principalType, principalSn, resourceSn);

        //如果存在ACL实例，则更新其授权
        if(acl != null){
            acl.setPermission(permission, yes);
//            getHibernateTemplate().update(acl);
            return;
        }

        //不存在ACL实例，则创建ACL实例
        acl = new Acl();
        acl.setPrincipalType(principalType);
        acl.setPrincipalSn(principalSn);
        acl.setResourceSn(resourceSn);
        acl.setPermission(permission, yes);
//        getHibernateTemplate().save(acl);
    }



    /**
     * 即时认证
     * 判断用户对某模块的某操作的授权（允许或不允许）
     * @param userId 用户标识
     * @param resourceSn 资源标识
     * @param permission 权限（C/R/U/D）
     * @return 允许（true）或不允许（false）
     */
    public boolean hasPermission(int userId, int resourceSn, int permission) throws NamingException {

        //查找直接授予用户的授权
        Acl acl = findACL(Acl.TYPE_USER, userId, resourceSn);

        if(acl!= null){
            int yesOrNo = acl.getPermission(permission);

        //如果是确定的授权
            if(yesOrNo!= Acl.ACL_NEUTRAL){
                return yesOrNo == Acl.ACL_YES ? true : false;
            }
        }

        //继续查找用户的角色授权(优先级别由高到低)
        //将数据库更换为ldap
        String hql = "select r.id from UsersRoles ur join ur.role r join ur.user u "+
                "whereu.id = ? order by ur.orderNo asc";
        Acl acl1 = new Acl();
        //添加用户id
        acl1.setUserid("");
        List aclIds = null;

        //依照角色优先级依次查找其授权
        for(Iterator iter = aclIds.iterator(); iter.hasNext();) {
            Integer rid = (Integer) iter.next();
            acl= findACL(Acl.TYPE_ROLE, rid, resourceSn);

            //一旦发现授权，即可返回结果
            if(acl!= null){
                return acl.getPermission(permission) == Acl.ACL_YES ? true : false;
            }
        }

        return false;
    }

    /**
     * 搜索某个用户拥有读取权限的模块列表（用于登录，形成导航菜单的时候）
     * @param userId 用户标识
     * @return 模块列表（即列表的元素是Module对象）
     */
    public List searchModules(int userId) throws NamingException {

        //定义临时变量
        Map temp = new HashMap();

        //按优先级从低到高查找用户拥有的角色
        String hql = "select r.id from UsersRoles ur join ur.role r join ur.user u "+
                "whereu.id = ? order by ur.orderNo desc";
        Acl acl1 = new Acl();
        //添加用户id
        acl1.setUserid("");
        List aclIds = null;

        //依次循环角色,合并权限，优先级高的授权将覆盖优先级低的授权
        for(Iterator iter = aclIds.iterator(); iter.hasNext();) {
            Integer rid = (Integer) iter.next();

            //根据角色获得角色拥有的授权列表
            List acls = findRoleACLs(rid);

            //把授权放入临时变量
            for(Iterator iterator = acls.iterator(); iterator.hasNext();) {
                Acl acl = (Acl) iterator.next();
                //因为角色是按优先级从低到高遍历的，所以对于同一资源，优先级低的会被优先级高的覆盖
                temp.put(acl.getResourceSn(),acl);
            }
        }

        //查找直接授予用户的授权列表
        List acls = findUserACLs(userId);
        for(Iterator iter = acls.iterator(); iter.hasNext();) {
            Acl acl = (Acl) iter.next();
            //同样，对于同一资源，会覆盖掉继承而来的权限，从而达到不继承的目的
            temp.put(acl.getResourceSn(),acl);
        }

        //现在已获得用户拥有的所有授权（包括直接授予用户的以及用户继承角色的授权）
        //遍历所有权限，如果没有读取权限，则在临时变量中删除这个授权

        //遍历授权列表，如果没有读取权限，则临时存放在list中记录下来（map结构在遍历时进行remove操作会影响结果）
        List delResources = new ArrayList();
        Set entries = temp.entrySet();
        for(Iterator iter = entries.iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            Acl acl = (Acl)entry.getValue();

            if(acl.getPermission(Permission.READ)== Acl.ACL_NO){
                delResources.add(entry.getKey());
            }
        }

        //在临时变量中删除这些需要删除的授权
        for(Iterator iter = delResources.iterator(); iter.hasNext();) {
            Object key = (Object) iter.next();
            temp.remove(key);
        }

        //如果授权列表是空的，则返回0长度的集合
        if(temp.isEmpty()){
            return new ArrayList();
        }

        //现在已获得用户拥有读取权限的授权
        //更换为ldap
        String searchModules = "select m from Module m where m.id in (:ids)";
//        return getSession().createQuery(searchModules)
//                .setParameterList("ids",temp.keySet())
//                .list();
        return null;
    }
    //根据用户id查找用户的acl
    private List findUserACLs(int userId) {
        return null;
    }
    //根据角色id查找角色的acl
    private List findRoleACLs(Integer rid) {
        return null;
    }

    private ResourceLdap getHibernateTemplate() throws NamingException {
        ResourceLdapImpl ldap = new ResourceLdapImpl();
        ldap.connect();
        return ldap;
    }
    //根据主体类型、sn和资源的sn查找acl
    private Acl findACL(String principalType, int principalSn, int resourceSn) {

        return null;
    }
}
