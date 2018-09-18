package api;

import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;

import javax.naming.NamingException;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-17 上午11:02
 */

public class TestLdap
{
    public static void main(String[] args) throws NamingException
    {
        Ldap ldap=new LdapImpl();
        ldap.connect();
        try {
            String ou= "1";
            String sn= "3";
            String cn= "4";
            ldap.add(ou,sn,cn);
//            String old = "ou=ss,dc=example,dc=com";
//            String neew = "ou=dd";
//            ldap.updateNodes(old,neew);
//            String s = "123456";
//            String dn = "ou=dd";
//            ldap.update(s,dn);
//            ldap.search(s);
//            String dn = "ou=sxy,dc=example,dc=com";
//            ldap.delete(dn);
        } finally {
            ldap.close();
        }

    }
}

