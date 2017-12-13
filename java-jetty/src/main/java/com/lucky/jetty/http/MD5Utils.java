package com.lucky.jetty.http;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:35 2017/12/13
 */
public class MD5Utils {
    static Logger log = LoggerFactory.getLogger(MD5Utils.class);

    private MD5Utils() {
        throw new IllegalAccessError("Utility class");
    }

    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception var7) {
            log.error("string2MD5 e:{}", Throwables.getStackTraceAsString(var7));
            return "";
        }

        byte[] byteArray = inStr.getBytes();
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();

        for(int i = 0; i < md5Bytes.length; ++i) {
            int val = md5Bytes[i] & 255;
            if(val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static String convertMD5(String inStr) {
        char[] a = inStr.toCharArray();

        for(int i = 0; i < a.length; ++i) {
            a[i] = (char)(a[i] ^ 116);
        }

        return new String(a);
    }
}
