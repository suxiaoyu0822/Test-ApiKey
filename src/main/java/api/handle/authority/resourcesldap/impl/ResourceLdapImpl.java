package api.handle.authority.resourcesldap.impl;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.ResourcePartEntity;
import api.handle.util.PropertyUtil;

import javax.naming.*;
import javax.naming.directory.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-19 上午11:23
 */

public class ResourceLdapImpl implements ResourceLdap {
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
            dc = new InitialDirContext(env);
            System.out.println("认证成功!");
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("认证失败");
        } catch (Exception e) {
            System.out.println("认证出错：" + e);
        }
    }

    @Override
    public void saveRule(ResourcePartEntity resourcePartEntity) throws NamingException {
        BasicAttributes attrs = new BasicAttributes();
        Date date = new Date();
        String value = String.valueOf(date.getTime());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdayTime = dateFormat.format(date);
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("NewOC-rule");
        attrs.put(objclassSet);
        attrs.put("rule-id",value);
        attrs.put("client-ip-range", resourcePartEntity.getClient_ip_range());
        attrs.put("server-ip-range", resourcePartEntity.getServer_ip_range());
        attrs.put("start-time", resourcePartEntity.getStart_time());
        attrs.put("end-time", resourcePartEntity.getEnd_time());
        attrs.put("create-time",nowdayTime);
//        attrs.put("create-time","2018-11-03 14:49:35");
        attrs.put("user-name", resourcePartEntity.getUser_name());
        attrs.put("group-name", resourcePartEntity.getGroup_name());
        attrs.put("action",resourcePartEntity.getAction());
        dc.createSubcontext("rule-id="+value+","+resourcePartEntity.getDn(), attrs);
        System.out.println("saveRule Success!");
    }

    @Override
    public void saveo(ResourcePartEntity resourcePartEntity) throws NamingException {

    }

    @Override
    public void saveSecurityOU(ResourcePartEntity resourcePartEntity) throws NamingException {
        BasicAttributes attrs = new BasicAttributes();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdayTime = dateFormat.format(date);
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("NewOC-security");
        attrs.put(objclassSet);
        attrs.put("security-id",resourcePartEntity.getSecurity_id());
        attrs.put("security-name",resourcePartEntity.getSecurity_name());
        attrs.put("description",resourcePartEntity.getDescription());
        attrs.put("create-time",nowdayTime);
        dc.createSubcontext("security-id="+resourcePartEntity.getSecurity_id()+","+resourcePartEntity.getDn(), attrs);
        System.out.println("saveSecurityOU Success!");
    }

    @Override
    public void saveResourcesOU(ResourcePartEntity resourcePartEntity) throws NamingException {
        BasicAttributes attrs = new BasicAttributes();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdayTime = dateFormat.format(date);
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("NewOC-resources");
        attrs.put(objclassSet);
        attrs.put("resources-id",resourcePartEntity.getResources_id());
//        attrs.put("resources-name",resourcePartEntity.getResources_name());
        attrs.put("security-id",resourcePartEntity.getSecurity_id());
//        attrs.put("security-name",resourcePartEntity.getSecurity_name());
//        attrs.put("description",resourcePartEntity.getDescription());
//        attrs.put("create-time",nowdayTime);
        Name resources_id = new CompositeName().add(resourcePartEntity.getResources_id());
        dc.createSubcontext("resources-id="+resources_id+","+resourcePartEntity.getDn(), attrs);
        System.out.println("saveResourcesOU Success!");
    }


    @Override
    public boolean delete(String dn) throws NamingException
    {
        boolean b=false;
        try {
            dc.destroySubcontext(dn);
            b=true;
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Exception in delete():" + e);
            b=false;
        }
        return b;
    }

    @Override
    public boolean updateRule(ResourcePartEntity resourcePartEntity) {
        boolean b=false;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdayTime = dateFormat.format(date);
        try {
            ModificationItem[] mods = new ModificationItem[8];
            /* 修改属性 */
            Attribute attr0 = new BasicAttribute("client-ip-range",resourcePartEntity.getClient_ip_range());
            Attribute attr1 = new BasicAttribute("server-ip-range",resourcePartEntity.getServer_ip_range());
            Attribute attr2 = new BasicAttribute("start-time",resourcePartEntity.getStart_time());
            Attribute attr3 = new BasicAttribute("end-time",resourcePartEntity.getEnd_time());
            Attribute attr4 = new BasicAttribute("user-name",resourcePartEntity.getUser_name());
            Attribute attr5 = new BasicAttribute("group-name",resourcePartEntity.getGroup_name());
            Attribute attr6 = new BasicAttribute("action",resourcePartEntity.getAction());
            Attribute attr7 = new BasicAttribute("create-time",nowdayTime);
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr1);
            mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr2);
            mods[3] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr3);
            mods[4] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr4);
            mods[5] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr5);
            mods[6] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr6);
            mods[7] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr7);
            dc.modifyAttributes(resourcePartEntity.getDn(), mods);
            b=true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            b=false;
        }
        return b;

    }

    @Override
    public Boolean updatenode(ResourcePartEntity resourcePartEntity) {
        try {
            dc.rename(resourcePartEntity.getDn(),resourcePartEntity.getNewdn());
            return true;
        } catch (NamingException ne) {
            System.err.println("Error: " + ne.getMessage());
            return false;
        }

    }
    @Override
    public boolean updateNodes(ResourcePartEntity resourcePartEntity) throws NamingException
    {
        boolean b=false;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdayTime = dateFormat.format(date);
        try {
            ModificationItem[] mods = new ModificationItem[3];
            /* 修改属性 */
            Attribute attr0 = new BasicAttribute("security-name",resourcePartEntity.getSecurity_name());
            Attribute attr1 = new BasicAttribute("description",resourcePartEntity.getDescription());
            Attribute attr2 = new BasicAttribute("create-time",nowdayTime);
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr1);
            mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr2);
            /* 删除属性 */
//             Attribute attr0 = new BasicAttribute("description", updt);
//             mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attr0);
            /* 添加属性 */
//            Attribute attr0 = new BasicAttribute("description", updt);
//            mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);
            /* 修改 */
            dc.modifyAttributes(resourcePartEntity.getDn(), mods);
            b=true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            b=false;
        }
        return b;
    }
    @Override
    public boolean updateResource(ResourcePartEntity resourcePartEntity) throws NamingException
    {
        boolean b=false;
        try {
            ModificationItem[] mods = new ModificationItem[1];
            /* 修改属性 */
            Attribute attr0 = new BasicAttribute("security-id",resourcePartEntity.getSecurity_id());
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
            /* 删除属性 */
//             Attribute attr0 = new BasicAttribute("description", updt);
//             mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attr0);
            /* 添加属性 */
//            Attribute attr0 = new BasicAttribute("description", updt);
//            mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);
            /* 修改 */
            dc.modifyAttributes("resources-id="+resourcePartEntity.getResources_id()+","+resourcePartEntity.getDn(), mods);
            b=true;
        } catch (NamingException e) {
//            e.printStackTrace();
//            System.err.println("Error: " + e.getMessage());
            b=false;
        }
        return b;
    }
    @Override
    public String find(String dn,String searchFilter,String retAttrs) throws NamingException {
        List list = new ArrayList();
        String retstring = null;
        SearchControls searchCtls=new SearchControls();
        //对应当前查询的一个OBJECT节点
//        searchCtls.setSearchScope(SearchControls.OBJECT_SCOPE);
        //对应当前节点的下1级(仅仅1级)节点
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        //对应当前节点的所有子节点
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//        String searchFilter="rule-id="+resourcePartEntity.getRule_id();
        String returnedAttrs[]={retAttrs};
        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(dn,searchFilter,searchCtls);
        while(entries.hasMoreElements()){
            SearchResult entry=entries.next();
            Attributes attrs=entry.getAttributes();
            if(attrs!=null){
                for(NamingEnumeration<? extends Attribute> names=attrs.getAll();names.hasMore();){
                    Attribute attr=names.next();
                    for(NamingEnumeration<?> e =attr.getAll();e.hasMore();){
                        retstring=e.next().toString();
//                        list.add(description);
                    }
                }
            }
        }
        return retstring;
    }
    @Override
    public List findRuleList(String dn, String searchFilter, String retAttrs) throws NamingException {
        List list = new ArrayList();
        String retstring = null;
        SearchControls searchCtls=new SearchControls();
        //对应当前查询的一个OBJECT节点
//        searchCtls.setSearchScope(SearchControls.OBJECT_SCOPE);
        //对应当前节点的下1级(仅仅1级)节点
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        //对应当前节点的所有子节点
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//        String searchFilter="rule-id="+resourcePartEntity.getRule_id();
        String returnedAttrs[]={retAttrs};
        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(dn,searchFilter,searchCtls);
        while(entries.hasMoreElements()){
            SearchResult entry=entries.next();
            Attributes attrs=entry.getAttributes();
            if(attrs!=null){
                for(NamingEnumeration<? extends Attribute> names=attrs.getAll();names.hasMore();){
                    Attribute attr=names.next();
                    for(NamingEnumeration<?> e =attr.getAll();e.hasMore();){
                        retstring=e.next().toString();
                        list.add(retstring);
                    }
                }
            }
        }
        return list;
    }
    @Override
    public List findAllResourceList(String dn, String searchFilter, String retAttrs) throws NamingException {
        List list = new ArrayList();
        String retstring = null;
        SearchControls searchCtls=new SearchControls();
        //对应当前查询的一个OBJECT节点
//        searchCtls.setSearchScope(SearchControls.OBJECT_SCOPE);
        //对应当前节点的下1级(仅仅1级)节点
//        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        //对应当前节点的所有子节点
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String returnedAttrs[]={retAttrs};
        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(dn,searchFilter,searchCtls);
        while(entries.hasMoreElements()){
            SearchResult entry=entries.next();
            Attributes attrs=entry.getAttributes();
            if(attrs!=null){
                for(NamingEnumeration<? extends Attribute> names=attrs.getAll();names.hasMore();){
                    Attribute attr=names.next();
                    for(NamingEnumeration<?> e =attr.getAll();e.hasMore();){
                        retstring=e.next().toString();
                        list.add(retstring);
                    }
                }
            }
        }
        return list;
    }
    @Override
    public List findSecurityList(String dn, String searchFilter, String returnedAttrs[]) throws NamingException {
        List list = new ArrayList();
        String retstring = null;
        SearchControls searchCtls=new SearchControls();
        //对应当前查询的一个OBJECT节点
//        searchCtls.setSearchScope(SearchControls.OBJECT_SCOPE);
        //对应当前节点的下1级(仅仅1级)节点
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        //对应当前节点的所有子节点
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//        String searchFilter="rule-id="+resourcePartEntity.getRule_id();
        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(dn,searchFilter,searchCtls);
        while(entries.hasMoreElements()){
            SearchResult entry=entries.next();
            Attributes attrs=entry.getAttributes();
            if(attrs!=null){
                for(NamingEnumeration<? extends Attribute> names=attrs.getAll();names.hasMore();){
                    Attribute attr=names.next();
                    for(NamingEnumeration<?> e =attr.getAll();e.hasMore();){
                        retstring=e.next().toString();
                        list.add(retstring);
                    }
                }
            }
        }
        return list;
    }
    @Override
    public List FindAllAttribut(ResourcePartEntity resourcePartEntity) throws NamingException
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
        String returnedAttrs[]={"rule-id"};
        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(resourcePartEntity.getDn(),searchFilter,searchCtls);
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
    public List FindAll(ResourcePartEntity resourcePartEntity) throws NamingException
    {
        List list = new ArrayList();
        SearchControls searchCtls=new SearchControls();
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        String searchFilter="rule-id="+resourcePartEntity.getRule_id();
//        String returnedAttrs[]={"cn","sn","mail"};
//        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(resourcePartEntity.getDn(), searchFilter, searchCtls);
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
    public List FindAllSom(String dn, String searchFilter, String returnedAttrs[]) throws NamingException
    {
        List list = new ArrayList();
        SearchControls searchCtls=new SearchControls();
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//        String returnedAttrs[]={"cn","sn","mail"};
        searchCtls.setReturningAttributes(returnedAttrs);
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

    @Override
    public boolean isExistInLDAP(String dn) {
        boolean b=false;
        try {
            Attributes attrs = dc.getAttributes(dn);
            System.out.println(attrs);
            b=true;
        }catch (Exception e){
            b = false;
        }

        return b;
    }
}
