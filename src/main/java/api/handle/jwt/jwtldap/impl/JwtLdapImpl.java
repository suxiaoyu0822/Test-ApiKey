package api.handle.jwt.jwtldap.impl;

import api.handle.dto.JWTEntity;
import api.handle.dto.ResourcePartEntity;
import api.handle.jwt.jwtldap.JwtLdap;
import api.handle.util.PropertyUtil;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-22 下午3:21
 */

public class JwtLdapImpl implements JwtLdap {
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
    public void saveToken(JWTEntity jwtEntity) throws NamingException {
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("top");
        objclassSet.add("NewOC-jwt");
        attrs.put(objclassSet);
        attrs.put("appId",jwtEntity.getAppId());
        attrs.put("alg",jwtEntity.getAlg());
        attrs.put("typ",jwtEntity.getTyp());
        attrs.put("iss",jwtEntity.getIss());
        attrs.put("sub",jwtEntity.getSub());
        attrs.put("aud",jwtEntity.getAud());
        attrs.put("iat",jwtEntity.getIat());
        attrs.put("nbf",jwtEntity.getNbf());
        attrs.put("exp",jwtEntity.getExp());
        attrs.put("jti",jwtEntity.getJti());
        attrs.put("token",jwtEntity.getToken());
        dc.createSubcontext("appId="+jwtEntity.getAppId()+","+jwtEntity.getDn(), attrs);
        System.out.println("saveToken Success!");
    }

    @Override
    public boolean delete(String dn) throws NamingException {
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
    public boolean updateJwt(String dn,String iat,String token) {
        boolean b=false;
        try {
            ModificationItem[] mods = new ModificationItem[3];
            /* 修改属性 */
            Attribute attr0 = new BasicAttribute("iat",iat);
            Attribute attr1 = new BasicAttribute("nbf",iat);
            Attribute attr2 = new BasicAttribute("token",token);
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr1);
            mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr2);
            dc.modifyAttributes(dn, mods);
            b=true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            b=false;
        }
        return b;

    }
    @Override
    public String find(String dn, String searchFilter, String retAttrs) throws NamingException {
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
                    }
                }
            }
        }
        return retstring;
    }
    @Override
    public List FindJwtAttributes(String dn, String searchFilter, String returnedAttrs[]) throws NamingException
    {
        List list = new ArrayList();
        SearchControls searchCtls=new SearchControls();
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
//        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setReturningAttributes(returnedAttrs);
        NamingEnumeration<SearchResult> entries=dc.search(dn, searchFilter, searchCtls);
        while(entries.hasMoreElements()){
            SearchResult entry=entries.next();
            Attributes attrs=entry.getAttributes();
            if(attrs!=null){
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
            b=true;
        }catch (Exception e){
            b = false;
        }

        return b;
    }
}
