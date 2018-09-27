package api.handle.ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-21 下午3:26
 */

public class ConnectWithLdaps {
    private static DirContext dc;
    public static void main(String[] args) throws NamingException, NoSuchAlgorithmException {

        Hashtable env = new Hashtable();
        String LDAPS_URL = "ldaps://localhost:10636";
//        String LDAPS_URL = "ldap://localhost:10389";
        String adminName = "uid=admin,ou=system"; // 用户名
        String adminPassword = "secret"; // 密码
        // Simple bind
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAPS_URL);
//        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);


        DirContext ctx = new InitialDirContext(env);
        NamingEnumeration enm = ctx.list("");
        while (enm.hasMore()) {
            System.out.println(enm.next());
        }

        enm.close();
        ctx.close();
//        try {
//            dc = new InitialDirContext(env);// 初始化上下文
//            System.out.println("认证成功");
//        } catch (javax.naming.AuthenticationException e) {
//            System.out.println("认证失败");
//        } catch (Exception e) {
//            System.out.println("认证出错：" + e);
//        }
    }
}