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
        String searchBase = "DC=example,DC=COM";
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
                                System.out.println(user);
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
    public void add(String ou,String sn,String cn) throws NamingException
    {
        String root = "dc=register,dc=com"; // LDAP的根节点的DC
        BasicAttributes attrs = new BasicAttributes();
        attrs.put("ou",ou);
        attrs.put("sn",sn);
        attrs.put("cn",cn);
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("organizationalPerson");
        attrs.put(objclassSet);
        dc.createSubcontext("ou="+ ou + "," + root, attrs);

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

