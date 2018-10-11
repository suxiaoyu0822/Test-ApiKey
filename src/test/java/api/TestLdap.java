package api;

import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;
import io.netty.util.internal.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.naming.NamingException;
import java.util.ArrayList;
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
//        try {
            String o= "jiaojing";
//            String o= null;
            String ou= "xunluozu";
            String sn= "xj";
            String cn= "jpc";
            String password = "12345678";
            String uid = "cn=gg,ou=shenpizu,o=guanlizu,dc=registry,dc=baotoucloud,dc=com";
            String address ="baotou";
            String email = "1234567890@yahoo.com";
            String company ="d2";
            String telephoneNumber = "1234567892";
//            String description = "BeQuote";
//            String description = "BeQuote";
            String description = "Quote";

//
////                ldap.addO(o);
//            ldap.addEntry(o,ou,sn,cn,password,company,address,email,telephoneNumber,description);

        String dn = "ou=keyuan,ou=zuzhibu,o=zhengfu,dc=registry,dc=baotoucloud,dc=com";
        ldap.addUidEntry(dn,sn,cn,password,uid,description);
//        ldap.addDNEntry(dn,sn,cn,password,company,address,email,telephoneNumber,description);
//        ldap.addOUDN(dn);
//        String p="sxy";
//        List l = ldap.searchall(p,dn);
//        for (int i = 0;i<l.size();i++){
//            System.out.println(l.get(i));
//        }
//            String old = "ou=ss,dc=example,dc=com";
//            String neew = "ou=dd";
//            ldap.updateNodes(old,neew);

//        String ret = "uid";
//        String s=ldap.searchOne(cn,dn,ret);
//        System.out.println(s);
//        boolean b =ldap.isExistInLDAP(s);
//        System.out.println(b);

            String keyword="description";
            String data="Quote";
//            String dn = "cn=jpc,ou=zhiqinzu,o=jiaojing,dc=registry,dc=baotoucloud,dc=com";
//            ldap.update(keyword,data,dn);
//            String s = "test";
//            ldap.search(s);

//            ldap.delete(dn);

//            String dn = "dc=registry,dc=baotoucloud,dc=com";
//            String dn = "o=jiaojing,dc=registry,dc=baotoucloud,dc=com";
//            String dn = "ou=xunluozu,o=jiaojing,dc=registry,dc=baotoucloud,dc=com";
//            String Attribut = "cn";
//            List list=ldap.searchForAttribut(Attribut,dn);
//            for (int i=0;i<list.size();i++){
//                System.out.println(list.get(i));
//
//                List list1=ldap.searchall(String.valueOf(list.get(i)),dn);
//                Map<String,String> map = new LinkedHashMap<>();
//                for (int j=0;j<list1.size();j++){
//                    String key =StringUtils.substringBeforeLast(String.valueOf(list1.get(j)),": ");
//                    String value =StringUtils.substringAfterLast(String.valueOf(list1.get(j)),": ");
//                    map.put(key,value);
//                }
//                System.out.println(map);
//            }



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
//            String para="sxy";
//            String dn = "cn=sxy,ou=zhiqinzu,o=jiaojing,dc=registry,dc=baotoucloud,dc=com";
//            List list=ldap.searchall(para,dn);
//            Map<String,String> map = new LinkedHashMap<>();
//            for (int i=0;i<list.size();i++){
//                String key =StringUtils.substringBeforeLast(String.valueOf(list.get(i)),": ");
//                String value =StringUtils.substringAfterLast(String.valueOf(list.get(i)),": ");
//                map.put(key,value);
//            }
//            System.out.println(map);
//            JSONObject jsonObject = JSONObject.fromObject(map);
//            String cn = jsonObject.getString("cn");
//            String sn = jsonObject.getString("sn");
//            String l = jsonObject.getString("l");
//            System.out.println(cn+"+"+sn+"+"+l);
//        } finally {
//            ldap.close();
//        }

    }
}

