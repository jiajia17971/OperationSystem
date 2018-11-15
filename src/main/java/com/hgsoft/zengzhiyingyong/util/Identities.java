package com.hgsoft.zengzhiyingyong.util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 短UUID..
     */
    public static String uuid3() {
        long val = ByteBuffer.wrap(uuid2().toString().getBytes()).getLong();
        return Long.toString(val, Character.MAX_RADIX);
    }

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割，16位.
	 */
	public static String uuid4() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
	}


	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

}
