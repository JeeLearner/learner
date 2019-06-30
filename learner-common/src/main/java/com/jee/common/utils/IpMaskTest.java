package com.jee.common.utils;

import java.util.List;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/6/5
 * @Version:v1.0
 */
public class IpMaskTest {

    private static final String IP = "172.31.0.5";
    private static final String MASK = "30";

    public static void main(String[] args) {
        //1.获得起始IP和终止IP的方法（包含网络地址和广播地址）
        String startIp= IpMaskUtils.getBeginIpStr(IP, MASK);
        String endIp = IpMaskUtils.getEndIpStr(IP, MASK);
        System.out.println("起始IP：" + startIp + "终止IP：" + endIp);

        //2.获得起始IP和终止IP的方法（不包含网络地址和广播地址）
        String subStart=startIp.split("\\.")[0]+"."+startIp.split("\\.")[1]+"."+startIp.split("\\.")[2]+".";
        String subEnd=endIp.split("\\.")[0]+"."+endIp.split("\\.")[1]+"."+endIp.split("\\.")[2]+".";
        startIp=subStart+(Integer.parseInt(startIp.split("\\.")[3])+1);
        endIp=subEnd+(Integer.parseInt(endIp.split("\\.")[3])-1);
        System.out.println("起始IP：" + startIp + "终止IP：" + endIp);

        //3.判断一个IP是否属于某个网段
        boolean flag = IpMaskUtils.isInRange("10.2.0.0", "10.3.0.0/17");
        System.out.println(flag);

        //4.根据掩码位数查询可用IP数量（含网络地址和广播地址，可用的-2即可）
        int ipCount = IpMaskUtils.getIpCount("8");
        System.out.println("可用总量：" + ipCount + ",可用ip地址量：" + (ipCount -2));

        //5.判断是否是一个IP
        System.out.println(IpMaskUtils.isIP("192.168.1.0"));

        //6.把ip转换为数字(mysql中inet_aton()的实现)[目前无实用场景]
        System.out.println(IpMaskUtils.ipToDouble("192.168.1.1"));

        //7.ip的long类型与string类型互转
        System.out.println(IpMaskUtils.ipToLong(IP));
        System.out.println(IpMaskUtils.longToIp(2887712773L));

        //8.根据掩码位获取掩码
        System.out.println(IpMaskUtils.getMaskByMaskBit(MASK));

        //9.根据IP和位数返回该IP网段的所有可用IP（IP过多会内存溢出）
        List<String> ips = IpMaskUtils.parseIpMaskRange(IP, MASK);
        ips.forEach(ip -> {
            System.out.println("某网段Ip集合：" + ip);
        });

        //10.计算子网大小
        System.out.println(IpMaskUtils.getPoolMax(Integer.parseInt(MASK)));

        //11.根据子网掩码转换为掩码位 如 255.255.255.252转换为掩码位 为 30
        System.out.println(IpMaskUtils.getNetMask("255.255.255.252"));
        System.out.println(IpMaskUtils.getNetMask(IP));
    }
}

