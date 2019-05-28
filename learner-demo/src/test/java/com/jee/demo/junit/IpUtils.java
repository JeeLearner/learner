package com.jee.demo.junit;

import org.junit.Test;

/**
 * Create by wuzhuang 2018/08/16
 */
public class IpUtils {

    /**
     * ip转为十进制
     */
    public static long ipToLong(String ipAddress) {

        long result = 0;

        String[] ipAddressInArray = ipAddress.split("\\.");

        for (int i = 3; i >= 0; i--) {

            long ip = Long.parseLong(ipAddressInArray[3 - i]);

            result |= ip << (i * 8);

        }

        return result;
    }

    // example : 192.168.1.2
    public long ipToLong2(String ipAddress) {

        // ipAddressInArray[0] = 192
        String[] ipAddressInArray = ipAddress.split("\\.");

        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {

            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);

            // 1. 192 * 256^3
            // 2. 168 * 256^2
            // 3. 1 * 256^1
            // 4. 2 * 256^0
            result += ip * Math.pow(256, power);

        }

        return result;

    }

    /**
     * 十进制转为ip
     */
    public static String longToIp(long i) {

        return ((i >> 24) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + (i & 0xFF);
    }

    public String longToIp2(long ip) {
        StringBuilder sb = new StringBuilder(15);

        for (int i = 0; i < 4; i++) {

            // 1. 2
            // 2. 1
            // 3. 168
            // 4. 192
            sb.insert(0, Long.toString(ip & 0xff));

            if (i < 3) {
                sb.insert(0, '.');
            }

            // 1. 192.168.1.2
            // 2. 192.168.1
            // 3. 192.168
            // 4. 192
            ip = ip >> 8;

        }

        return sb.toString();
    }


    @Test
    public void test1() {
        long start = ipToLong("101.177.9.99");
        long middle = ipToLong("177.199.199.220");
        long end = ipToLong("192.168.1.199");
        System.out.println(start);
        System.out.println(ipToLong("101.177.9.100"));
        System.out.println(middle);
        System.out.println(end);
        System.out.println(middle > start); //true
        System.out.println(middle < end);//true
    }
}
