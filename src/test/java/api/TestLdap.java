package api;

import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;
import io.netty.util.internal.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.naming.NamingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
//            String o= "a";
////            String o= null;
//            String ou= "aa";
//            String sn= "aaa";
//            String cn= "aaaa";
//            String password = "a";
//            String address ="b";
//            String email = "c";
//            String company ="d";
//            String telephoneNumber = "e";
//
////                ldap.addO(o);
////                ldap.addOU(o,ou);
//            ldap.addEntry(o,ou,sn,cn,password,address,email,company,telephoneNumber);

//            String old = "ou=ss,dc=example,dc=com";
//            String neew = "ou=dd";
//            ldap.updateNodes(old,neew);
//            String keyword="sn";
//            String data="xxy";
//            String dn = "cn=sxy,ou=zhiqinzu,o=jiaojing,dc=registry,dc=baotoucloud,dc=com";
//            ldap.update(keyword,data,dn);
//            String s = "test";
//            ldap.search(s);

//            LdapImpl ldap1 = new LdapImpl();
//            String rdn= "o=FirePolice,dc=register,dc=com";
//            String rdn= "o=s,dc=register,dc=com";
//            String rdn= "ou=people,o=FirePolice,dc=register,dc=com";
//            String rdn= "ou=s,o=FirePolice,dc=register,dc=com";
//            ldap1.connect();
//            boolean b=ldap1.isExistInLDAP(rdn);
//            System.out.println(b);
//            String dn = "o=FirePolice,dc=register,dc=com";
//            ldap.delete(dn);
//            String d= "ee";
//            ldap.addDC(d);
            String para="sxy";
            String dn = "cn=sxy,ou=zhiqinzu,o=jiaojing,dc=registry,dc=baotoucloud,dc=com";
            List list=ldap.searchall(para,dn);
            Map<String,String> map = new LinkedHashMap<>();
            for (int i=0;i<list.size();i++){
                String key =StringUtils.substringBeforeLast(String.valueOf(list.get(i)),": ");
                String value =StringUtils.substringAfterLast(String.valueOf(list.get(i)),": ");
                map.put(key,value);
            }
            System.out.println(map);
            JSONObject jsonObject = JSONObject.fromObject(map);
            String cn = jsonObject.getString("cn");
            String sn = jsonObject.getString("sn");
            String l = jsonObject.getString("l");
            System.out.println(cn+"+"+sn+"+"+l);
        } finally {
            ldap.close();
        }

    }
}

