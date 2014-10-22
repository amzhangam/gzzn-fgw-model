package com.gzzn.fgw.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.CipherService;

public abstract class EncryptAndDecryptUtil {

	private static final byte[] key = KeyGenerator.getKey();
	private static CipherService service = new AesCipherService();

	public static String encrypt(String value) {
		return service.encrypt(value.getBytes(), key).toBase64();
	}

	public static String decrypt(String value) {
		return new String(service.decrypt(Base64.decode(value.getBytes()), key).getBytes());
	}
}
