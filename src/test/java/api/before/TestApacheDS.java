package api.before;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.cursor.SearchCursor;
import org.apache.directory.api.ldap.model.entry.*;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.*;
import org.apache.directory.api.ldap.model.message.controls.ManageDsaITImpl;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-17 上午9:29
 */

public class TestApacheDS {
    LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
    //检测连接状态
    @Test
    public void testSimpleBindRequest() throws LdapException {
        connection.bind("uid=admin,ou=system", "secret");
        connection.unBind();
        Assert.assertFalse(connection.isConnected());
        Assert.assertFalse(connection.isAuthenticated());
    }
    //简单的查询
    @Test
    public void testSimplesearch() throws LdapException, CursorException,IOException {
        connection.bind("uid=admin,ou=system", "secret");
        EntryCursor cursor = connection.search("ou=system", "(objectclass=*)",SearchScope.ONELEVEL);
        while (cursor.next()) {
            Entry entry = cursor.get();
            Assert.assertNotNull(entry);
            System.out.println(entry);
        }
        cursor.close();
        connection.close();
    }
    //带有约束条件的查询
    @Test
    public void testComplexSearches() throws LdapException, CursorException,IOException {
        connection.bind("uid=admin,ou=system", "secret");
        SearchRequest req = new SearchRequestImpl();
        req.setScope(SearchScope.SUBTREE);
        req.addAttributes("*");
        req.setTimeLimit(0);
        req.setBase(new Dn("ou=system"));
        req.setFilter("(ou=consumers)");

        SearchCursor searchCursor = connection.search(req);
        while (searchCursor.next()) {
            Response response = searchCursor.get();
            if (response instanceof SearchResultEntry) {
                Entry resultEntry = ((SearchResultEntry) response).getEntry();
                Assert.assertNotNull(resultEntry);
                System.out.println(resultEntry);
            }
        }
        searchCursor.close();
        connection.close();
    }

    //添加条目
    @Test
    public void testAddLdif() throws LdapException, IOException {
        connection.bind("uid=admin,ou=system", "secret");
        connection.add(new DefaultEntry("cn=testadd,ou=system","ObjectClass:top", "ObjectClass:person", "cn:testadd_cn","sn:testadd_sn"));
        connection.close();

    }

    //以请求的方式添加条目
    @Test
    public void testAddWithControl() throws LdapException, IOException {
        connection.bind("uid=admin,ou=system", "secret");
        Entry entry = new DefaultEntry("cn=testadd2,ou=system","ObjectClass:top", "ObjectClass:person", "sn:testadd_sn");
        AddRequest addRequest = new AddRequestImpl();
        addRequest.setEntry(entry);
        addRequest.addControl(new ManageDsaITImpl());
        AddResponse response = connection.add(addRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(ResultCodeEnum.SUCCESS, response.getLdapResult().getResultCode());
        connection.close();
    }

    //删除条目
    @Test
    public void testDeleteLeafNode() throws LdapException, IOException {
        connection.bind("uid=admin,ou=system", "secret");
        connection.delete("cn=testadd,ou=system");
        connection.close();
    }
    //删除条目
    @Test
    public void testModify() throws LdapException, IOException {
        connection.bind("uid=admin,ou=system", "secret");
        Modification addedGivenName = new DefaultModification(ModificationOperation.ADD_ATTRIBUTE, "givenName");        connection.modify("uid=Doe,dc=acme,dc=com",addedGivenName);
        connection.close();
    }
}
