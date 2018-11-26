package api.handle.authority.permission;

import api.handle.authority.acl.Acl;
import api.handle.authority.resourcesldap.impl.ResourceLdapImpl;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-22 下午4:39
 */

public class TelnetSwitchboard {
    private TelnetClient telnet = new TelnetClient("VT100");

    private InputStream in;

    private PrintStream out;

    private static final String DEFAULT_AIX_PROMPT = "#";
    private static final String ENTER_COMMAND_ARROW = ">";
    private static final String ENTER_COMMAND_BRACKETS = "]";
    private static final String ENTER="\n";


    /**
     * telnet 端口
     */
    private String port;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    /**
     * IP 地址
     */
    private String ip;

    public TelnetSwitchboard(String ip, String user, String password) {
        this.ip = ip;
        this.port = String.valueOf(23);
        this.user = user;
        this.password = password;
    }

    public TelnetSwitchboard(String ip, String port, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    /**
     * @return boolean 连接成功返回true，否则返回false
     */
    private boolean connect() {

        boolean isConnect = true;

        try {

            telnet.connect(ip, Integer.parseInt(port));
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
            telnet.setKeepAlive(true);
            write(password);
            String msg=readUntil(ENTER_COMMAND_ARROW);
            System.out.println(msg);
            write("system-view");
            msg=readUntil("\n");
            System.out.println(msg);
            msg=readUntil("\n");
            System.out.println(msg);
            write("display interface ");
            msg=readUntil("\n");
            System.out.println(msg);
            msg=readUntil(ENTER_COMMAND_BRACKETS);
            System.out.println(msg);

        } catch (Exception e) {
            isConnect = false;
            e.printStackTrace();
            return isConnect;
        }
        return isConnect;
    }

    public void su(String user, String password) {
        try {
            write("su" + " - " + user);
            readUntil("Password:");
            write(password);
            readUntil(DEFAULT_AIX_PROMPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            while (true) {
                //System.out.print(ch);// ---需要注释掉
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String value) {
        try {
            out.println(value);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(DEFAULT_AIX_PROMPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getNowDate() {
        this.connect();
        String nowDate = this.sendCommand("date|awk '{print $2,$3,$4}'");
        String[] temp = nowDate.split("\r\n");
        // 去除命令字符串
        if (temp.length > 1) {
            nowDate = temp[0];
        } else {
            nowDate = "";
        }
        this.disconnect();
        return nowDate;
    }
    public void AddAclToSwitchboard(String description) throws IOException {
//        telnet.connect(ip, Integer.parseInt(port));
        out = new PrintStream(telnet.getOutputStream());
        telnet.setKeepAlive(true);
        write(password);
        String msg=readUntil(ENTER_COMMAND_ARROW);
        System.out.println(msg);
        //在交换机中添加ACL规则
        write("system-view");
        write(description);
        msg=readUntil("\n");
        System.out.println(msg);
        msg=readUntil("\n");
        System.out.println(msg);
//        write("display interface ");
//        msg=readUntil("\n");
//        System.out.println(msg);
        msg=readUntil(ENTER_COMMAND_BRACKETS);
        System.out.println(msg);

    }
//    public static void main(String[] args) {
//        try {
//            TelnetSwitchboard telnet = new TelnetSwitchboard("10.10.2.249", "Huawei", "Huawei");
//            System.setOut(new PrintStream("D:/telnet.txt"));
//            telnet.connect();
//            telnet.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
public static void main(String[] args) {
    try {
        TelnetSwitchboard telnet = new TelnetSwitchboard("10.10.2.249", "Huawei", "Huawei");
        System.setOut(new PrintStream("/home/sxy/sxyTest/ACL/telnet.txt"));
        Acl acl = new Acl();
        acl.setUserid("1234567");
        acl.setAttribut("description");
        ResourceLdapImpl resourceLdap = new ResourceLdapImpl();
        resourceLdap.connect();
//        String description=resourceLdap.find(acl);
//        telnet.AddAclToSwitchboard(description);
//        resourceLdap.close();
//        telnet.disconnect();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
