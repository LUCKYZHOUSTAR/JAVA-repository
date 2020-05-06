package com.lucky.code;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lucky
 * @created: 2020/05/05 22:27
 */
public class ZSolution {

    public static String convert(String s, int numRows) {
        if (numRows < 2) { return s; }
        List<StringBuilder> rows = new ArrayList<StringBuilder>();
        for (int i = 0; i < numRows; i++) { rows.add(new StringBuilder()); }
        int i = 0, flag = -1;
        for (char c : s.toCharArray()) {
            rows.get(i).append(c);
            if (i == 0 || i == numRows - 1) { flag = -flag; }
            i += flag;
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder row : rows) { res.append(row); }
        return res.toString();
    }

    public static void main(String[] args) {
        String s = "werwerwer";
        System.out.println(convert(s, 3));
    }
}

