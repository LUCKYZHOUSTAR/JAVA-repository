/**
 * Jiedaibao.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package basic.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author bindi.zhang
 * @version $Id: RegularExpression.java, v 0.1 2016年5月12日 下午5:39:06 bindi.zhang Exp $
 */
public class RegularExpression {
    /** 数字 */
    public static final String NUMBER                = "^[0-9]\\d*$";
    /** 数字(非全0) */
    public static final String NUMBER_AMT            = "^\\d*[1-9]\\d*$";
    /** 数字(开头非零) */
    public static final String INTEGER               = "^[1-9]\\d*$";
    /** 金额 */
    public static final String AMT                   = "^(([1-9]\\d{0,17})|0)(\\.\\d{1,2})?$";
    /** 数字取值范围 */
    public static final String NUMBER_RANGE          = "^(([1-9]\\d*)|0)\\~[1-9]\\d*$";
    /** 手机号 */
    public static final String MOBILE_NUMBER         = "^1[345789]\\d{9}$";
    /** 短信码 */
    public static final String NUMBER_SMS6           = "^\\d{6}$";
    /** 中文 */
    public static final String CHINESE               = "^[\\u4E00-\\u9FA5\\uFF00-\\uFFFF]*$";
    /** 混合中文 */
    public static final String CHINESE_SPECIAL       = "^[A-Za-z\\d\\u4E00-\\u9FA5\\uFF00-\\uFFFF,，.。\\-（）()・]*$";
    /** 姓名 */
    public static final String NAME                  = "^([\\u4E00-\\u9FA5\\uFF00-\\uFFFF.・]*|[a-zA-Z]*|[a-zA-Z]*\\s{1}[a-zA-Z]*)$";
    /** Email */
    public static final String EMAIL                 = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /** 中英混合 */
    public static final String NUMBER_ENGLISH        = "^[0-9a-zA-Z]+$";
    /** 全大写英文 */
    public static final String BIG_ENGLISH           = "^[A-Z]+$";
    /** 银行卡号 */
    public static final String BANK_CARD_NO          = "^[13-689]\\d{13,18}$";
    /** 中英混合(中间可以带"-"或"_") */
    public static final String NUMBER_ENGLISH_ORD_NO = "^[0-9a-zA-Z]+[-_]*[0-9a-zA-Z]+$";
    /** http url */
    public static final String HTTP_URL              = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$";
    /** 外部订单号 */
    public static final String REGEX_OUT_ORDER_ID    = "^[0-9a-zA-Z]+[-_]*[0-9a-zA-Z]+$";

    public static boolean check(String str,String regular) {
        Pattern pattern = Pattern.compile(regular,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    
    public static boolean checkMoney(BigDecimal moeny) {
        String str=moeny.toString();
        Pattern pattern = Pattern.compile(AMT,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    
    public static void main(String[] args) {
//        String str="ceponline@yahoo.com";
        String str="1234567891123456789.00";
        Pattern pattern = Pattern.compile(AMT,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.matches());
    }
    
}
