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

    List searchall(String uid, String dn) throws NamingException;

    String searchInitials(String uid, String dn) throws NamingException;

    String searchOne(String uid, String dn, String ret) throws NamingException;

    List searchForAttribut(String Attribut, String dn) throws NamingException;

    boolean updateNodes(String oldDN, String newDN) throws NamingException;

    boolean update(String cn, String userPassword, String givenName, String employeeType, String initials, String mail, String telephoneNumber, String description, String dn) throws NamingException;

    void addDC(String d) throws NamingException;

    void addO(String o) throws NamingException;

    void addODN(String dn) throws NamingException;

    void addOU(String o, String ou) throws NamingException;

    void addOUDN(String dn) throws NamingException;

    void addEntry(String o, String ou, String sn, String cn, String password, String company, String address, String email, String telephoneNumber, String description) throws NamingException;

//    void addEntryACI(String dn, String cn, String prescriptiveACI, String subtreeSpecification) throws NamingException;

    void addUidEntry(String dn, String sn, String cn, String password, String uid, String description) throws NamingException;

    void delete(String dn) throws NamingException;

    boolean isExistInLDAP(String rdn) throws NamingException;

    boolean isExistInAttr(String dn) throws NamingException;

    void close() throws NamingException;

    void addDNEntry(String dn, String uid, String sn, String username, String password, String givenName, String employeeType,String initials, String email, String telephoneNumber, String description) throws NamingException;
}

