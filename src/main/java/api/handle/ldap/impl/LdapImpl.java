package api.handle.ldap.impl;

import api.handle.ldap.Ldap;
import api.handle.util.PropertyUtil;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.sn;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-17 上午11:00
 */

public class LdapImpl implements Ldap
{
    private DirContext dc;
    Properties properties = PropertyUtil.loadProperties("ldap.properties");
    @Override
    public synchronized void connect() throws NamingException
    {
        Hashtable env = new Hashtable();
        String LDAP_URL = properties.getProperty("ldap.url"); // LDAP 访问地址
        String adminName = properties.getProperty("adminName"); // 用户名
        String adminPassword = properties.getProperty("adminPassword");// 密码
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        try {
            dc = new InitialDirContext(env);// 初始化上下文
            System.out.println("认证成功");
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("认证失败");
        } catch (Exception e) {
            System.out.println("认证出错：" + e);
        }
    }

    @Override
    public void search(String uid) throws NamingException
    {
        // 创建搜索控件
        SearchControls searchCtls = new SearchControls();
        // 设置搜索范围
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // 设置搜索过滤条件
        String searchFilter = "uid=" + uid;
        // 设置搜索域节点
        String searchBase = "dc=register,dc=com";
        // 定制返回属性
        String returnedAtts[] = { "url", "whenChanged", "employeeID", "name", "userPrincipalName", "physicalDeliveryOfficeName", "departmentNumber", "telephoneNumber", "homePhone", "mobile", "department", "sAMAccountName", "whenChanged", "mail" };
        // 不定制属性，返回所有的属性集
        // searchCtls.setReturningAttributes(null);
        int totalResults = 0;
        try {
            NamingEnumeration answer = dc.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes Attrs = sr.getAttributes();
                if (Attrs != null) {
                    try {
                        for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore();) {
                            Attribute Attr = (Attribute) ne.next();
                            // 读取属性值
                            for (NamingEnumeration e = Attr.getAll(); e.hasMore(); totalResults++) {
                                // 接受循环遍历读取的userPrincipalName用户属性
                                String user = e.next().toString();
                                System.out.println("---"+user);
                            }
                            // 读取属性值
//                             Enumeration values = Attr.getAll();
//                             if (values != null) {
//                                      while (values.hasMoreElements()) {
//                                               System.out.println(" cn=" + values.nextElement());
//                                      }
//                             }
                        }
                    } catch (NamingException e) {
                        System.err.println("Throw Exception : " + e);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Throw Exception : " + e);
        }
    }

    @Override
    public List searchall(String uid, String dn) throws NamingException
    {
        List list = new ArrayList();
        SearchControls searchCtls=new SearchControls();
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        String searchFilter="uid="+uid;
//        String returnedAttrs[]={"cn","sn","mail"};
//        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(dn, searchFilter, searchCtls);
        while(entries.hasMoreElements()){
            SearchResult entry=entries.next();
            Attributes attrs=entry.getAttributes();
            if(attrs!=null){
//                for(NamingEnumeration<? extends Attribute> names=attrs.getAll();names.hasMore();){
//                    Attribute attr=names.next();
                    for(NamingEnumeration<?> e =attrs.getAll();e.hasMore();){
                        list.add(e.next().toString());
                    }
//                }
            }
        }
        return list;
    }
    @Override
    public String searchInitials(String uid, String dn) throws NamingException
    {
        String initials = null;
        SearchControls searchCtls=new SearchControls();
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        String searchFilter="uid="+uid;
        String returnedAttrs[]={"initials"};
        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(dn, searchFilter, searchCtls);
        while(entries.hasMoreElements()){
            SearchResult entry=entries.next();
            Attributes attrs=entry.getAttributes();
            if(attrs!=null){
                for(NamingEnumeration<? extends Attribute> names=attrs.getAll();names.hasMore();){
                    Attribute attr=names.next();
                for(NamingEnumeration<?> e =attr.getAll();e.hasMore();){
                    initials=e.next().toString();
                }
                }
            }
        }
        return initials;
    }

    @Override
    public String searchOne(String uid, String dn,String ret) throws NamingException
    {
        String one = null;
        SearchControls searchCtls=new SearchControls();
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        String searchFilter="uid="+uid;
        String returnedAttrs[]={ret};
        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(dn, searchFilter, searchCtls);
        while(entries.hasMoreElements()){
            SearchResult entry=entries.next();
            Attributes attrs=entry.getAttributes();
            if(attrs!=null){
                for(NamingEnumeration<? extends Attribute> names=attrs.getAll();names.hasMore();){
                    Attribute attr=names.next();
                    for(NamingEnumeration<?> e =attr.getAll();e.hasMore();){
                        one=e.next().toString();
                    }
                }
            }
        }
        return one;
    }
    @Override
    public List searchForAttribut(String Attribut,String dn) throws NamingException
    {
        List list = new ArrayList();
        String description = null;
        SearchControls searchCtls=new SearchControls();
        //对应当前查询的一个OBJECT节点
//        searchCtls.setSearchScope(SearchControls.OBJECT_SCOPE);
        //对应当前节点的下1级(仅仅1级)节点
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        //对应当前节点的所有子节点
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String searchFilter="objectClass=*";
        String returnedAttrs[]={Attribut};
        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(dn,searchFilter,searchCtls);
        while(entries.hasMoreElements()){
            SearchResult entry=entries.next();
            Attributes attrs=entry.getAttributes();
            if(attrs!=null){
                for(NamingEnumeration<? extends Attribute> names=attrs.getAll();names.hasMore();){
                    Attribute attr=names.next();
                    for(NamingEnumeration<?> e =attr.getAll();e.hasMore();){
                        description=e.next().toString();
                        list.add(description);
                    }
                }
            }
        }
        return list;
    }
    @Override
    public boolean updateNodes(String oldDN, String newDN) throws NamingException
    {
        try {
            dc.rename(oldDN, newDN);
            return true;
        } catch (NamingException ne) {
            System.err.println("Error: " + ne.getMessage());
            return false;
        }
    }
    @Override
    public boolean update(String cn, String userPassword, String givenName, String employeeType,String initials, String mail, String telephoneNumber, String description, String dn) throws NamingException
    {
        try {
            ModificationItem[] mods = new ModificationItem[8];
            /* 修改属性 */
             Attribute attr0 = new BasicAttribute("cn",cn);
             Attribute attr1 = new BasicAttribute("userPassword",userPassword);
             Attribute attr2 = new BasicAttribute("givenName",givenName);
             Attribute attr3 = new BasicAttribute("employeeType",employeeType);
             Attribute attr4 = new BasicAttribute("initials",initials);
             Attribute attr5 = new BasicAttribute("mail",mail);
             Attribute attr6 = new BasicAttribute("telephoneNumber",telephoneNumber);
             Attribute attr7 = new BasicAttribute("description",description);
             mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
             mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr1);
             mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr2);
             mods[3] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr3);
             mods[4] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr4);
             mods[5] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr5);
             mods[6] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr6);
             mods[7] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr7);
            /* 删除属性 */
//             Attribute attr0 = new BasicAttribute("description", updt);
//             mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attr0);
            /* 添加属性 */
//            Attribute attr0 = new BasicAttribute("description", updt);
//            mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);
            /* 修改 */
            dc.modifyAttributes(dn, mods);
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    @Override
    public void addDC(String d) throws NamingException {
        String root = "dc=com";// LDAP的根节点的DC
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("dcObject");
        objclassSet.add("organization");
        attrs.put(objclassSet);
        attrs.put("dc",d);
        attrs.put("o",d);
        dc.createSubcontext("dc="+d+","+root, attrs);
    }

    @Override
    public void addO(String o) throws NamingException {
        String root = "o="+o+",dc=registry,dc=baotoucloud,dc=com";
//        String root = "o="+o+",dc=aa,dc=com";
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("organization");
        attrs.put(objclassSet);
        attrs.put("o",o);
        dc.createSubcontext(root, attrs);
        System.out.println("addO Success!");
    }
    @Override
    public void addODN(String dn) throws NamingException {
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("organization");
        attrs.put(objclassSet);

        attrs.put("administrativeRole","accessControlSpecificArea");

        dc.createSubcontext(dn, attrs);
        System.out.println("addODN Success!");
    }
    @Override
    public void addOU(String o, String ou) throws NamingException {
        String root = "ou="+ou+",o="+o+",dc=registry,dc=baotoucloud,dc=com";
//        String root = "ou="+ou+",o="+o+",dc=aa,dc=com";
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("organizationalUnit");
        attrs.put(objclassSet);
        attrs.put("ou",ou);
        dc.createSubcontext(root, attrs);
        System.out.println("addOU Success!");
    }

    @Override
    public void addOUDN(String dn) throws NamingException {
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("organizationalUnit");
        attrs.put(objclassSet);
        dc.createSubcontext(dn, attrs);
        System.out.println("addOU Success!");
    }
    @Override
    public void addEntry(String o, String ou, String sn, String cn, String password, String company, String address, String email,String telephoneNumber, String description) throws NamingException {
        String root = "cn="+cn+",ou="+ou+",o="+o+",dc=registry,dc=baotoucloud,dc=com";
//        String root = "cn="+cn+",ou="+ou+",o="+o+",dc=aa,dc=com";
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("inetOrgPerson");
        attrs.put(objclassSet);
        attrs.put("sn",sn);
        attrs.put("cn",cn);
        attrs.put("userPassword",password);
        attrs.put("registeredAddress",company);
        attrs.put("l",address);
        attrs.put("telephoneNumber",telephoneNumber);
        attrs.put("mail",email);
        attrs.put("description",description);
        dc.createSubcontext(root, attrs);
        System.out.println("addEntry Success!");
    }

//    @Override
//    public void addEntryACI(String dn, String cn, String prescriptiveACI, String subtreeSpecification) throws NamingException {
////        String root = "cn="+cn+",ou="+ou+",o="+o+",dc=registry,dc=baotoucloud,dc=com";
////        String root = "cn="+cn+",ou="+ou+",o="+o+",dc=aa,dc=com";
//        BasicAttributes attrs = new BasicAttributes();
//        BasicAttribute objclassSet = new BasicAttribute("objectClass");
//        objclassSet.add("top");
//        objclassSet.add("subentry");
//        objclassSet.add("accessControlSubentry");
//        attrs.put(objclassSet);
//        attrs.put("cn",cn);
//        attrs.put("prescriptiveACI",prescriptiveACI);
//        attrs.put("subtreeSpecification",subtreeSpecification);
//        dc.createSubcontext("cn="+cn+","+dn, attrs);
//        System.out.println("addEntry Success!");
//    }

    @Override
    public void addDNEntry(String dn, String uid, String sn, String username, String password, String givenName, String employeeType,String initials, String email, String telephoneNumber, String description) throws NamingException {
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("inetOrgPerson");
        attrs.put(objclassSet);
        attrs.put("uid",uid);
        attrs.put("sn",sn);
        attrs.put("cn",username);
        attrs.put("userPassword",password);
        attrs.put("givenName",givenName);
        attrs.put("telephoneNumber",telephoneNumber);
        attrs.put("mail",email);
        attrs.put("employeeType",employeeType);
        attrs.put("initials",initials);
        attrs.put("description",description);
//        dc.createSubcontext("cn="+username+","+dn, attrs);
        dc.createSubcontext("uid="+uid+","+dn, attrs);
        System.out.println("addEntry Success!");
    }
    @Override
    public void addUidEntry(String dn, String sn, String cn, String password, String uid,String description) throws NamingException {
//        String root = "cn="+cn+",ou="+ou+",o="+o+",dc=aa,dc=com";
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("inetOrgPerson");
        attrs.put(objclassSet);
        attrs.put("sn",sn);
        attrs.put("cn",cn);
        attrs.put("userPassword",password);
        attrs.put("description",description);
        attrs.put("uid",uid);
        dc.createSubcontext("cn="+cn+","+dn, attrs);
        System.out.println("addEntry Success!");
    }

    @Override
    public void delete(String dn) throws NamingException
    {
        try {
            dc.destroySubcontext(dn);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in delete():" + e);
        }
    }

    @Override
    public boolean isExistInLDAP(String rdn) throws NamingException {
//        // 创建搜索控件
//        SearchControls searchCtls = new SearchControls();
//        // 设置搜索范围
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//        // 设置搜索过滤条件
//        String searchFilter =rdn;
//        // 设置搜索域节点
//        String searchBase = "dc=register,dc=com";
//        NamingEnumeration answer = dc.search(searchBase,searchFilter,searchCtls);
        boolean b=false;
        try {
            Attributes attrs = dc.getAttributes(rdn);
            b=true;
        }catch (Exception e){
//            System.out.println(e);
            b = false;
        }
//        while (answer.hasMoreElements()) {
//            b=true;
//            break;
//        }

        return b;
    }

    @Override
    public boolean isExistInAttr(String dn) throws NamingException {
        boolean b=false;
        SearchControls searchCtls=new SearchControls();
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        String searchFilter="cn=lxj";
        NamingEnumeration<SearchResult> entries=dc.search(dn, searchFilter, searchCtls);
        while(entries.hasMoreElements()){
            try {
                SearchResult entry=entries.next();
                b=true;
            } catch (NamingException e) {
//                e.printStackTrace();
                b=false;
            }

        }
        return b;
    }
    @Override
    public void close() throws NamingException
    {
        if (dc != null) {
            try {
                dc.close();
            } catch (NamingException e) {
                System.out.println("NamingException in close():" + e);
            }
        }
    }


}

