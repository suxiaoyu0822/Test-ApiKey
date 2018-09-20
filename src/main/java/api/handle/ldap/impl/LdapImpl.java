package api.handle.ldap.impl;

import api.handle.ldap.Ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-17 上午11:00
 */

public class LdapImpl implements Ldap
{
    private DirContext dc;

    @Override
    public synchronized void connect() throws NamingException
    {
        Hashtable env = new Hashtable();
        String LDAP_URL = "ldap://localhost:10389"; // LDAP 访问地址
        String adminName = "uid=admin,ou=system"; // 用户名
        String adminPassword = "secret"; // 密码
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
    public void search(String userName) throws NamingException
    {
        // 创建搜索控件
        SearchControls searchCtls = new SearchControls();
        // 设置搜索范围
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // 设置搜索过滤条件
        String searchFilter = "cn=" + userName;
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
    public boolean update(String updt, String dn) throws NamingException
    {
        try {
            ModificationItem[] mods = new ModificationItem[1];
            /* 修改属性 */
             Attribute attr0 = new BasicAttribute("uid", updt);
             mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
            /* 删除属性 */
//             Attribute attr0 = new BasicAttribute("description", updt);
//             mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attr0);
            /* 添加属性 */
//            Attribute attr0 = new BasicAttribute("description", updt);
//            mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);
            /* 修改 */
            dc.modifyAttributes(dn + ",dc=example,dc=com", mods);
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
    public void addEntry(String o, String ou, String sn, String cn, String password, String company, String address, String email,String telephoneNumber) throws NamingException {
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
        dc.createSubcontext(root, attrs);
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

