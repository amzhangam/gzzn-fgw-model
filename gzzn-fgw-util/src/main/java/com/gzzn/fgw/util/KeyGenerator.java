package com.gzzn.fgw.util;

import org.apache.shiro.codec.Hex;

/**
 * AES算法加解密密钥
 * 
 * @author yjfeng
 * @date 2013-1-25
 * @version v1.0
 */
public abstract class KeyGenerator {

	private static final String KEY = "6536363138363962646438376133346534396464363861363662373038663963";

	private static final byte[] REAL_KEY;

	static {
		REAL_KEY = Hex.decode(Hex.decode(KEY));
	}

	/**
	 * 获取密钥
	 * 
	 * @return
	 */
	public static byte[] getKey() {
		return REAL_KEY;
	}

}
