package api.handle.util;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-5 下午7:09
 */

public class ReturnJudge {

    public String Judge(JSONObject jsonObject, String client_ip, String server_ip, String now_time, String user_name, String group_name,String action) {
        TimeStampAndLongUtil timeStampAndLongUtil = new TimeStampAndLongUtil();
        int end_time = Math.toIntExact(timeStampAndLongUtil.TimeToLong(jsonObject.getString("end-time")));
        int start_time =Math.toIntExact(timeStampAndLongUtil.TimeToLong(jsonObject.getString("start-time")));
        int nowtime = Math.toIntExact(timeStampAndLongUtil.TimeToLong(now_time));
        String username = jsonObject.getString("user-name");
        String groupname = jsonObject.getString("group-name");
        String server_ip_range = jsonObject.getString("server-ip-range");
        String client_ip_range = jsonObject.getString("client-ip-range");

//        String server_ip_range = "0";
//        String server_ip_range = "192.168.1.127";
//        String server_ip_range = "192.168.1.127/24";
//        String client_ip_range = "0";
//        String client_ip_range = "192.168.1.127";
//        String client_ip_range = "192.168.1.127/24";

//        System.out.println("client_ip_range:"+client_ip_range);
//        System.out.println("client_ip:"+client_ip);
//        System.out.println("server_ip_range:"+server_ip_range);
//        System.out.println("server_ip:"+server_ip);
//        System.out.println("start_time:"+start_time);
//        System.out.println("nowtime:"+nowtime);
//        System.out.println("end_time:"+end_time);
//        System.out.println("user_name:"+user_name);
//        System.out.println("username:"+username);
//        System.out.println("group_name:"+group_name);
//        System.out.println("groupname:"+groupname);


        Map<String,String> map = new HashMap<>();


        //server 不是合法ip
        if (!isIP(server_ip_range)){
            //server 是0或者ip段
            if (server_ip_range.equals("0")){
                //server 是0

                //client 不是合法ip
                if (!isIP(client_ip_range)){
                    //client 是0或者ip段
                    if (client_ip_range.equals("0")){
                        //client 是0
                        System.out.println("server=0,client=0");
                        if (client_ip_range.equals(client_ip)&&server_ip_range.equals(server_ip)&&nowtime> start_time&&nowtime<end_time&&user_name.equals(username)&&group_name.equals(groupname)){
                            action = jsonObject.getString("action");
                            System.out.println("匹配成功：××××××××××××××××××××××××××××××Judge action: "+action+" ××××××××××××××××××××××××");
                        }
                    }else {
                        //client 是ip段
                        System.out.println("server=0,client=ip段");
                        if (client_ip.equals("0")){
                            System.out.println("匹配失败：client=0,client_ip_range=ip段！");
                            return action;
                        }
                        if (isInRange(client_ip,client_ip_range)){
                            if (server_ip_range.equals(server_ip)&&nowtime> start_time&&nowtime<end_time&&user_name.equals(username)&&group_name.equals(groupname)){
                                action = jsonObject.getString("action");
                                System.out.println("匹配成功：××××××××××××××××××××××××××××××Judge action: "+action+" ××××××××××××××××××××××××");
                            }
                        }else {
                            System.out.println("匹配失败：client_ip不属于client_ip_range！");
                        }
                    }
                }else {
                    //client 是合法IP
                    System.out.println("server=0,client=合法ip");
                    if (client_ip_range.equals(client_ip)&&server_ip_range.equals(server_ip)&&nowtime> start_time&&nowtime<end_time&&user_name.equals(username)&&group_name.equals(groupname)){
                        action = jsonObject.getString("action");
                        System.out.println("匹配成功：××××××××××××××××××××××××××××××Judge action: "+action+" ××××××××××××××××××××××××");
                    }
                }

            }else {
                //server 是ip段

                //client 不是合法ip
                if (!isIP(client_ip_range)){
                    //client 是0或者ip段
                    if (client_ip_range.equals("0")){
                        //client 是0
                        System.out.println("server=ip段,client=0");
                        if (server_ip.equals("0")){
                            System.out.println("匹配失败：server=0,server_ip_range=ip段！");
                            return action;
                        }
                        if (isInRange(server_ip,server_ip_range)){
                            if (client_ip_range.equals(client_ip)&&nowtime> start_time&&nowtime<end_time&&user_name.equals(username)&&group_name.equals(groupname)){
                                action = jsonObject.getString("action");
                                System.out.println("匹配成功：××××××××××××××××××××××××××××××Judge action: "+action+" ××××××××××××××××××××××××");
                            }
                        }else {
                            System.out.println("匹配失败：server_ip不属于server_ip_range！");
                        }
                    }else {
                        //client 是ip段
                        System.out.println("server=ip段,client=ip段");
                        if (server_ip.equals("0")){
                            System.out.println("匹配失败：server=0,server_ip_range=ip段！");
                            return action;
                        }
                        if (isInRange(server_ip,server_ip_range)){

                            if (client_ip.equals("0")){
                                System.out.println("匹配失败：client=0,client_ip_range=ip段！");
                                return action;
                            }
                            if (isInRange(client_ip,client_ip_range)){
                                if (nowtime> start_time&&nowtime<end_time&&user_name.equals(username)&&group_name.equals(groupname)){
                                    action = jsonObject.getString("action");
                                    System.out.println("匹配成功：××××××××××××××××××××××××××××××Judge action: "+action+" ××××××××××××××××××××××××");
                                }
                            }else {
                                System.out.println("匹配失败：client_ip不属于client_ip_range！");
                            }
                        }else {
                            System.out.println("匹配失败：server_ip不属于server_ip_range！");
                        }
                    }
                }else {
                    //client 是合法IP
                    System.out.println("server=ip段,client=合法ip");
                    if (server_ip.equals("0")){
                        System.out.println("匹配失败：server=0,server_ip_range=ip段！");
                        return action;
                    }
                    if (isInRange(server_ip,server_ip_range)){
                        if (client_ip_range.equals(client_ip)&&nowtime> start_time&&nowtime<end_time&&user_name.equals(username)&&group_name.equals(groupname)){
                            action = jsonObject.getString("action");
                            System.out.println("匹配成功：××××××××××××××××××××××××××××××Judge action: "+action+" ××××××××××××××××××××××××");
                        }
                    }else {
                        System.out.println("匹配失败：server_ip不属于server_ip_range！");
                    }
                }

            }

        }else {
            //server是合法ip

            //client 不是合法ip
            if (!isIP(client_ip_range)){
                //client 是0或者ip段
                if (client_ip_range.equals("0")){
                    //client 是0
                    System.out.println("server=合法ip,client=0");
                    if (client_ip_range.equals(client_ip)&&server_ip_range.equals(server_ip)&&nowtime> start_time&&nowtime<end_time&&user_name.equals(username)&&group_name.equals(groupname)){
                        action = jsonObject.getString("action");
                        System.out.println("匹配成功：××××××××××××××××××××××××××××××Judge action: "+action+" ××××××××××××××××××××××××");
                    }
                    else {
                        System.out.println("匹配失败,ip不匹配！");
                    }
                }else {
                    //client 是ip段
                    System.out.println("server=合法ip,client=ip段");
                    if (client_ip.equals("0")){
                        System.out.println("匹配失败：client=0,client_ip_range=ip段！");
                        return action;
                    }
                    if (isInRange(client_ip,client_ip_range)){
                        if (server_ip_range.equals(server_ip)&&nowtime> start_time&&nowtime<end_time&&user_name.equals(username)&&group_name.equals(groupname)){
                            action = jsonObject.getString("action");
                            System.out.println("匹配成功：××××××××××××××××××××××××××××××Judge action: "+action+" ××××××××××××××××××××××××");
                        }
                    }else {
                        System.out.println("匹配失败：client_ip不属于client_ip_range！");
                    }
                }
            }else {
                //client 是合法IP
                System.out.println("server=合法ip,client=合法ip");
                if (client_ip_range.equals(client_ip)&&server_ip_range.equals(server_ip)&&nowtime> start_time&&nowtime<end_time&&user_name.equals(username)&&group_name.equals(groupname)){
                    action = jsonObject.getString("action");
                    System.out.println("匹配成功：××××××××××××××××××××××××××××××Judge action: "+action+" ××××××××××××××××××××××××");
                }
                else {
                    System.out.println("匹配失败,ip不匹配！");
                }
            }

        }

        return action;
    }
    /**
     * 功能：判断一个IP是不是在一个网段下的
     * 格式：isInRange("192.168.8.3", "192.168.9.10/22");
     */
    public boolean isInRange(String ip, String cidr) {
        //转义字符 将ip根据'.'区分开
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8)
                | Integer.parseInt(ips[3]);
        //获取掩码位数
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
//        System.out.println("type:"+type);
        //0x16进制,一个f代表4个1,所以就是2进制的32个1. 左移32-type位补0
        int mask = 0xFFFFFFFF << (32 - type);
//        System.out.println("mask:"+mask);
        //转义字符 将网段ip根据'.'区分开
        String cidrIp = cidr.replaceAll("/.*", "");
//        System.out.println("cidrIp:"+cidrIp);
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24)
                | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8)
                | Integer.parseInt(cidrIps[3]);
        return (ipAddr & mask) == (cidrIpAddr & mask);
    }

    /**
     * 功能：判断是否是一个IP
     * 格式：isIP("192.192.192.1")
     */
    public boolean isIP(String str) {
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }


    public static void main(String[] args) throws IOException {
        ReturnJudge returnJudge = new ReturnJudge();
//        System.out.println(returnJudge.isInRange("192.168.1.127", "192.168.1.64/24"));
//        System.out.println(returnJudge.isIP("555.555.555.555"));
        String ip= "0";
        System.out.println(ip.equals("0"));
        if (ip.equals("0")){
            System.out.println("======");
        }
    }
}
