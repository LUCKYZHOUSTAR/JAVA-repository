package cmc.lucky.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:26 2017/11/30
 */
public class CharCount {


    public static void main(String[] args) {
        System.out.println(getMaxCountStr("aaass"));
    }
    public static int getMaxCountStr(String s) {

        int max = 0; // 记录最大出现次数
        int[] cnt = new int[127]; // 临时计数用的数组
        for (int i = 0; i < s.length(); i++) { // 循环字符以做统计
            char c = s.charAt(i); // 取出单个字母
            max = (++cnt[c] > max) ? cnt[c] : max; // 计数并检测最大出现次数
        }

        return max;
    }
}
