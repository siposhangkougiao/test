package com.mtnz.util;

import java.util.UUID;

public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	public static void main(String[] args) {
		System.out.println(get32UUID());
		System.err.println(get19Date());
	}
	
	public static String get19Date() {
		String uuid = String.valueOf(System.currentTimeMillis())+Tools.getRandomNum();//得到时间戳+6位随机数
		return uuid;
	}
}
