package com.mtnz.util;

public class NumberUtil {

    public static Integer getNumber(){
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        return num;
    }
}
