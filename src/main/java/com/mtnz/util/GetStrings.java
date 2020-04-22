package com.mtnz.util;

import java.util.Random;

public class GetStrings {
    public static String getRandomNickname(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                int cc = random.nextInt(26);
                String aa = String.valueOf((choice + cc));
                while (true){
                    if(!aa.equals("O")&&!aa.equals("o")){
                        val += (char) (choice + random.nextInt(26));
                        break;
                    }
                }
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字 f9r9693
                int bb = random.nextInt(10);
                val += String.valueOf(bb);
            }
        }
        val = val.replace('O','h');
        val = val.replace('o','h');
        val = val.replace('0','r');
        return val;
    }
}
