package api.handle.ldap;

import javax.naming.NamingException;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-17 上午10:59
 */

public interface Ldap
{

    void connect() throws NamingException;
    void search(String para) throws NamingException;
    boolean updateNodes(String oldDN, String newDN) throws NamingException;
    boolean update(String employeeID, String dn) throws NamingException;

    void add(String ou, String sn, String cn,String password,String company,String address,String email) throws NamingException;

    void delete(String dn) throws NamingException;
    void close() throws NamingException;
}

