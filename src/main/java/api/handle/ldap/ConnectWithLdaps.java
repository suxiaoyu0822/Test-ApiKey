package api.handle.ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-21 下午3:26
 */

public class ConnectWithLdaps {
    private static DirContext dc;
    public static void main(String[] args) throws NamingException, NoSuchAlgorithmException, KeyManagementException {

        Hashtable env = new Hashtable();
        String LDAPS_URL = "ldaps://localhost:10636";
//        String LDAPS_URL = "ldap://localhost:10389";
        String adminName = "uid=admin,ou=system"; // 用户名
        String adminPassword = "secret"; // 密码
        String keystoreAsString = "/opt/java/jdk-10.0.1_linux-x64_bin/jdk-10.0.1/lib/security/cacerts";
        String keystorePwd = "changeit";
        // Simple bind

        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAPS_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);

//        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        System.setProperty("https.cipherSuites", "TLS_RSA_WITH_AES_128_CBC_SHA256,TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA");

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null,null,null);
        SSLContext.setDefault(context);
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

        System.setProperty("javax.net.ssl.trustStore" , keystoreAsString);
        System.setProperty("javax.net.ssl.trustStorePassword" , keystorePwd);

        env.put(Context.REFERRAL,"ignore");
        env.put(Context.SECURITY_PROTOCOL, "ssl");

        DirContext ctx = new InitialDirContext(env);
        NamingEnumeration enm = ctx.list("sxy");
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