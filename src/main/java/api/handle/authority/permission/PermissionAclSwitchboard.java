package api.handle.authority.permission;

import api.handle.authority.acl.Acl;
import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.authority.resourcesldap.impl.ResourceLdapImpl;

import javax.naming.NamingException;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-22 下午3:25
 */

public class PermissionAclSwitchboard {
    public static void main(String[] args) throws NamingException {
        //获取ACL规则
        Acl acl = new Acl();
        acl.setUserid("123456");
        acl.setDn("ou=API,o=resource,dc=resources,dc=baotoucloud,dc=com");
        acl.setAttribut("description");
//        String des = getHibernateTemplate().find(acl);
//        System.out.println("规则:"+des);
        //将获取的ACL应用到指定的交换机端口
    }

    public static ResourceLdap getHibernateTemplate() throws NamingException {
        ResourceLdapImpl ldap = new ResourceLdapImpl();
        ldap.connect();
        return ldap;
    }
}
