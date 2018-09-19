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
            String ou= "a";
            String sn= "b";
            String cn= "c";
            String password = "d";
            String address ="e";
            String email = "f";
            String company ="g";
            ldap.add(ou,sn,cn,password,company,address,email);
//            String old = "ou=ss,dc=example,dc=com";
//            String neew = "ou=dd";
//            ldap.updateNodes(old,neew);
//            String s = "123456";
//            String dn = "ou=dd";
//            ldap.update(s,dn);
//            ldap.search(s);
//            String dn = "ou=public,dc=register,dc=com";
//            ldap.delete(dn);
        } finally {
            ldap.close();
        }

    }
}

