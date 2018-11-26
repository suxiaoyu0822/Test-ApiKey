package api.authorityTest;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.authority.resourcesldap.impl.ResourceLdapImpl;

import javax.naming.NamingException;
import java.util.List;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-22 上午11:01
 */

public class TestResourceLdap {
    public static void main(String[] args) throws NamingException{
        ResourceLdap resourceLdap = new ResourceLdapImpl();
        resourceLdap.connect();
        String dn = "o=资源,dc=resources,dc=baotoucloud,dc=com";
        String searchFilter = "security-id=1542162118087";
        String retAttrs = "resources-id";
//        String searchFilter = "resources-id=App-v1-Add1";
//        String retAttrs = "security-id";
//        String list = resourceLdap.find(dn,searchFilter,retAttrs);
//        List list = resourceLdap.findRuleList(dn,searchFilter,retAttrs);
        List list = resourceLdap.findAllResourceList(dn,searchFilter,retAttrs);
        System.out.println(list);
//        resourceLdap.delete(searchFilter+","+dn);
        resourceLdap.close();
    }
}
