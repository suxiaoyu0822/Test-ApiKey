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
            String username = "ook";
            ldap.add(username);
//            String old = "ou=sxy,";
//            String neew = "ou=dd";
//            ldap.update(old,neew);
//            String s = "55";
//            String dn = "ou=sxy";
//            ldap.update(s,dn);
//            ldap.search(s);
//            String dn = "ou=sxy,dc=example,dc=com";
//            ldap.delete(dn);
        } finally {
            ldap.close();
        }

    }
}

