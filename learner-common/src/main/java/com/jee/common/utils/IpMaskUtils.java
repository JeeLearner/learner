package com.jee.common.utils;

/**
 * @Description: Ip-Mask工具类
 * @Auther: jeeLearner
 * @Date: 2019/1/1
 * @Version:v1.0
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 功能点：
 *   1.getBeginIpStr("172.31.0.5", "30")  getEndIpStr("172.31.0.5", "30")
 *       获得起始IP和终止IP的方法（包含网络地址和广播地址）
 *       也可以定制：获得起始IP和终止IP的方法（不包含网络地址和广播地址）
 *   2.isInRange("10.2.0.0", "10.3.0.0/17");
 *       判断一个IP是否属于某个网段
 *   3.getIpCount("30")
 *       根据掩码位数查询可用IP数量（含网络地址和广播地址，可用的-2即可）
 *   4.isIP("192.168.1.0")
 *       判断是否是一个IP
 *   5.ipToDouble("192.168.1.1")
 *       把ip转换为数字(mysql中inet_aton()的实现)[目前无实用场景]
 *   6.ipToLong("172.31.0.5")  longToIp(2887712773L)
 *       ip的long类型与string类型互转
 *   7.getMaskByMaskBit("30")
 *       根据掩码位获取掩码
 *   8.parseIpMaskRange("172.31.0.5", "30")
 *       根据IP和位数返回该IP网段的所有可用IP（IP过多会内存溢出）
 *   9.getPoolMax(30)
 *       计算子网大小
 *   10.getNetMask("172.31.0.5")
 *       根据子网掩码转换为掩码位 如 255.255.255.252转换为掩码位 为 30
 */
public class IpMaskUtils {

    /**
     * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 起始IP的字符串表示
     */
    public static String getBeginIpStr(String ip, String maskBit) {
        return longToIp(getBeginIpLong(ip, maskBit));
    }

    /**
     * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 起始IP的长整型表示
     */
    public static Long getBeginIpLong(String ip, String maskBit) {
        // & 位运算
        return ipToLong(ip) & ipToLong(getMaskByMaskBit(maskBit));
    }

    /**
     * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 终止IP的字符串表示
     */
    public static String getEndIpStr(String ip, String maskBit)
    {
        return longToIp(getEndIpLong(ip, maskBit));
    }

    /**
     * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 终止IP的长整型表示
     */
    public static Long getEndIpLong(String ip, String maskBit) {
        return getBeginIpLong(ip, maskBit) + ~ipToLong(getMaskByMaskBit(maskBit));
    }

    /**
     * 功能：判断一个IP是不是在一个网段下的
     * 格式：isInRange("192.168.8.3", "192.168.9.10/22");
     */
    public static boolean isInRange(String ip, String cidr) {
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
        int mask = 0xFFFFFFFF << (32 - type);
        String cidrIp = cidr.replaceAll("/.*", "");
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24)
                | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8)
                | Integer.parseInt(cidrIps[3]);

        return (ipAddr & mask) == (cidrIpAddr & mask);
    }

    /**
     * 功能：根据位数返回IP总数
     * 格式：parseIpMaskRange("192.192.192.1", "23")
     */
    public static int getIpCount(String mask) {
        return BigDecimal.valueOf(Math.pow(2, 32 - Integer.parseInt(mask))).setScale(0, BigDecimal.ROUND_DOWN).intValue();//IP总数，去小数点
    }


    /**
     * 功能：判断字符串是否是ip
     * 格式：isIP("192.192.192.1")
     */
    public static boolean isIP(String str) {
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    /**
     * 功能：将ip转为double
     * 格式：ipToDouble("192.192.192.1")
     */
    public static double ipToDouble(String ip) {
        String[] arr = ip.split("\\.");
        double d1 = Double.parseDouble(arr[0]);
        double d2 = Double.parseDouble(arr[1]);
        double d3 = Double.parseDouble(arr[2]);
        double d4 = Double.parseDouble(arr[3]);
        return d1 * Math.pow(256, 3) + d2 * Math.pow(256, 2) + d3 * 256 + d4;
    }

    /**
     * 把long类型的Ip转为一般Ip类型：xx.xx.xx.xx
     *
     * @param ip
     * @return
     */
    public static String longToIp(Long ip)
    {
        /*String s1 = String.valueOf((ip & 4278190080L) / 16777216L);
        String s2 = String.valueOf((ip & 16711680L) / 65536L);
        String s3 = String.valueOf((ip & 65280L) / 256L);
        String s4 = String.valueOf(ip & 255L);
        return s1 + "." + s2 + "." + s3 + "." + s4;*/
        return ((ip >> 24) & 0xFF) +
                "." + ((ip >> 16) & 0xFF) +
                "." + ((ip >> 8) & 0xFF) +
                "." + (ip & 0xFF);
    }

    /**
     * 把xx.xx.xx.xx类型的转为long类型的
     * ip: 172.31.0.4
     // 1. 172 * 256^3
     // 2. 31 * 256^2
     // 3. 0 * 256^1
     // 4. 4 * 256^0
     */
    public static Long ipToLong(String ip) {
        long result = 0;
        String[] ipAddressInArray = ip.split("\\.");
        for (int i = 3; i >= 0; i--) {
            long ipOfLong = Long.parseLong(ipAddressInArray[3 - i]);
            result |= ipOfLong << (i * 8);
        }

        return result;
    }

    /**
     * 根据掩码位获取掩码
     *
     * @param maskBit
     *            掩码位数，如"28"、"30"
     * @return
     */
    public static String getMaskByMaskBit(String maskBit)
    {
        return "".equals(maskBit) ? "error, maskBit is null !" : getMaskMap(maskBit);
    }


    /**
     * 根据maskBit查Mask
     * @param maskBit
     * @return
     */
    public static String getMaskMap(String maskBit) {
        String mask;
        switch (maskBit){
            case "1": mask = "128.0.0.0"; break;
            case "2": mask = "192.0.0.0"; break;
            case "3": mask = "224.0.0.0"; break;
            case "4": mask = "240.0.0.0"; break;
            case "5": mask = "248.0.0.0"; break;
            case "6": mask = "252.0.0.0"; break;
            case "7": mask = "254.0.0.0"; break;
            case "8": mask = "255.0.0.0"; break;
            case "9": mask = "255.128.0.0"; break;
            case "10": mask = "255.192.0.0"; break;
            case "11": mask = "255.224.0.0"; break;
            case "12": mask = "255.240.0.0"; break;
            case "13": mask = "255.248.0.0"; break;
            case "14": mask = "255.252.0.0"; break;
            case "15": mask = "255.254.0.0"; break;
            case "16": mask = "255.255.0.0"; break;
            case "17": mask = "255.255.128.0"; break;
            case "18": mask = "255.255.192.0"; break;
            case "19": mask = "255.255.224.0"; break;
            case "20": mask = "255.255.240.0"; break;
            case "21": mask = "255.255.248.0"; break;
            case "22": mask = "255.255.252.0"; break;
            case "23": mask = "255.255.254.0"; break;
            case "24": mask = "255.255.255.0"; break;
            case "25": mask = "255.255.255.128"; break;
            case "26": mask = "255.255.255.192"; break;
            case "27": mask = "255.255.255.224"; break;
            case "28": mask = "255.255.255.240"; break;
            case "29": mask = "255.255.255.248"; break;
            case "30": mask = "255.255.255.252"; break;
            case "31": mask = "255.255.255.254"; break;
            case "32": mask = "255.255.255.255"; break;
            default:   mask = "-1"; break;
        }
        return mask;
    }

    /**
     * 功能：根据IP和位数返回该IP网段的所有IP
     * 格式：parseIpMaskRange("192.192.192.1.", "23")
     */
    public static List<String> parseIpMaskRange(String ip, String mask){
        List<String> list=new ArrayList<>();
        if ("32".equals(mask)) {
            list.add(ip);
        }else{
            String startIp=getBeginIpStr(ip, mask);
            String endIp=getEndIpStr(ip, mask);
            if (!"31".equals(mask)) {
                String subStart=startIp.split("\\.")[0]+"."+startIp.split("\\.")[1]+"."+startIp.split("\\.")[2]+".";
                String subEnd=endIp.split("\\.")[0]+"."+endIp.split("\\.")[1]+"."+endIp.split("\\.")[2]+".";
                startIp=subStart+(Integer.parseInt(startIp.split("\\.")[3])+1);
                endIp=subEnd+(Integer.parseInt(endIp.split("\\.")[3])-1);
            }
            list=parseIpRange(startIp, endIp);
        }
        return list;
    }

    public static List<String> parseIpRange(String ipfrom, String ipto) {
        List<String> ips = new ArrayList<String>();
        String[] ipfromd = ipfrom.split("\\.");
        String[] iptod = ipto.split("\\.");
        int[] int_ipf = new int[4];
        int[] int_ipt = new int[4];
        for (int i = 0; i < 4; i++) {
            int_ipf[i] = Integer.parseInt(ipfromd[i]);
            int_ipt[i] = Integer.parseInt(iptod[i]);
        }
        for (int A = int_ipf[0]; A <= int_ipt[0]; A++) {
            for (int B = (A == int_ipf[0] ? int_ipf[1] : 0); B <= (A == int_ipt[0] ? int_ipt[1]
                    : 255); B++) {
                for (int C = (B == int_ipf[1] ? int_ipf[2] : 0); C <= (B == int_ipt[1] ? int_ipt[2]
                        : 255); C++) {
                    for (int D = (C == int_ipf[2] ? int_ipf[3] : 0); D <= (C == int_ipt[2] ? int_ipt[3]
                            : 255); D++) {
                        ips.add(A + "." + B + "." + C + "." + D);
                    }
                }
            }
        }
        return ips;
    }

    /**
     * 计算子网大小
     *
     * @param maskBit
     *            掩码位
     * @return
     */
    public static int getPoolMax(int maskBit)
    {
        if (maskBit <= 0 || maskBit >= 32)
        {
            return 0;
        }
        return (int) Math.pow(2, 32 - maskBit) - 2;
    }

    /**
     * 根据子网掩码转换为掩码位 如 255.255.255.252转换为掩码位 为 30
     *
     * @param netmarks
     * @return
     */
    public static int getNetMask(String netmarks)
    {
        StringBuilder sbf;
        String str;
        int inetmask = 0;
        int count = 0;
        String[] ipList = netmarks.split("\\.");
        for (int n = 0; n < ipList.length; n++)
        {
            sbf = toBin(Integer.parseInt(ipList[n]));
            str = sbf.reverse().toString();
            count = 0;
            for (int i = 0; i < str.length(); i++)
            {
                i = str.indexOf('1', i);
                if (i == -1)
                {
                    break;
                }
                count++;
            }
            inetmask += count;
        }
        return inetmask;
    }

    private static StringBuilder toBin(int x)
    {
        StringBuilder result = new StringBuilder();
        result.append(x % 2);
        x /= 2;
        while (x > 0)
        {
            result.append(x % 2);
            x /= 2;
        }
        return result;
    }
}

