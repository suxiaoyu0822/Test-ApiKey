package api.handle.ldap;

import javax.naming.NamingException;
import java.util.List;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-17 上午10:59
 */

public interface Ldap
{

    void connect() throws NamingException;
    void search(String para) throws NamingException;

    List searchall(String para, String dn) throws NamingException;

    boolean updateNodes(String oldDN, String newDN) throws NamingException;
    boolean update(String Keyword,String updt, String dn) throws NamingException;

    void addDC(String d) throws NamingException;
    void addO(String o) throws NamingException;
    void addOU(String o, String ou) throws NamingException;

    void addEntry(String o, String ou,String sn, String cn, String password, String company, String address, String email,String telephoneNumber) throws NamingException;

    void delete(String dn) throws NamingException;

    boolean isExistInLDAP(String rdn) throws NamingException;

    void close() throws NamingException;
}

