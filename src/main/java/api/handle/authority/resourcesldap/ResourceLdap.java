package api.handle.authority.resourcesldap;

import api.handle.dto.ResourcePartEntity;

import javax.naming.NamingException;
import java.util.List;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-19 上午11:24
 */

public interface ResourceLdap {

    void connect() throws NamingException;
    void saveRule(ResourcePartEntity resourcePartEntity) throws NamingException;
    void saveo(ResourcePartEntity resourcePartEntity) throws NamingException;
    void saveSecurityOU(ResourcePartEntity resourcePartEntity) throws NamingException;
    void saveResourcesOU(ResourcePartEntity resourcePartEntity) throws NamingException;
    boolean delete(String dn) throws NamingException;
    boolean updateRule(ResourcePartEntity resourcePartEntity);
    Boolean updatenode(ResourcePartEntity resourcePartEntity);

    boolean updateNodes(ResourcePartEntity resourcePartEntity) throws NamingException;

    boolean updateResource(ResourcePartEntity resourcePartEntity) throws NamingException;

    String find(String dn, String searchFilter, String retAttrs) throws NamingException;

    List findRuleList(String dn, String searchFilter, String retAttrs) throws NamingException;

    List findAllResourceList(String dn, String searchFilter, String retAttrs) throws NamingException;

    List findSecurityList(String dn, String searchFilter, String returnedAttrs[]) throws NamingException;

    List FindAllAttribut(ResourcePartEntity resourcePartEntity) throws NamingException;

    List FindAll(ResourcePartEntity resourcePartEntity) throws NamingException;

    List FindAllSom(String dn, String searchFilter, String returnedAttrs[]) throws NamingException;

    void close() throws NamingException;
    boolean isExistInLDAP(String dn);
}
