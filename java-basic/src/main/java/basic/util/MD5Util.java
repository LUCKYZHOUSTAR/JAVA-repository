package basic.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Arrays;

public class MD5Util {

    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建余额Tag
     * @param ac
     * @param capTyp
     * @param bal
     * @return
     * @author Jeffrey 2016年1月28日
     */
    public static String createBalanceMd5Tag(String ac, String capTyp, BigDecimal bal) {
    	DecimalFormat df = new DecimalFormat("#.00");
        String cryptSeed = ac + capTyp + df.format(bal);
        return createBalanceMd5Tag(cryptSeed);
    }
    
    public static String createBalanceMd5Tag(String cryptSeed) {
    	return MD5(cryptSeed, "chinaunicom");
    }

    public final static String MD5(String s1, String s2) {
        byte[] var3 = new byte[64];
        byte[] var4 = new byte[64];

        byte[] var5;
        byte[] var6;
        try {
            var5 = s2.getBytes("UTF-8");
            var6 = s1.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var10) {
            var5 = s2.getBytes();
            var6 = s1.getBytes();
        }

        Arrays.fill(var3, var5.length, 64, (byte) 54);
        Arrays.fill(var4, var5.length, 64, (byte) 92);

        for (int var7 = 0; var7 < var5.length; ++var7) {
            var3[var7] = (byte) (var5[var7] ^ 54);
            var4[var7] = (byte) (var5[var7] ^ 92);
        }

        MessageDigest var11 = null;

        try {
            var11 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var9) {
            return null;
        }

        var11.update(var3);
        var11.update(var6);
        byte[] var8 = var11.digest();
        var11.reset();
        var11.update(var4);
        var11.update(var8, 0, 16);
        var8 = var11.digest();
        return toHex(var8);
    }

    private static String toHex(byte[] var0) {
        if (var0 == null) {
            return null;
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 2);

            for (int var2 = 0; var2 < var0.length; ++var2) {
                int var3 = var0[var2] & 255;
                if (var3 < 16) {
                    var1.append("0");
                }

                var1.append(Integer.toString(var3, 16));
            }

            return var1.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.MD5("20121221"));
        System.out.println(MD5Util.MD5(""));
    }
}
