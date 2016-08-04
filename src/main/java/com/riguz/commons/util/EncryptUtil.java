package com.riguz.commons.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;

/**
 * 字符串常用加密/解密工具类.
 *
 * RSA 参考 RSA
 */
@SuppressWarnings("restriction")
public class EncryptUtil {
    public static String encrypt(String method, String message) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance(method);
            digest.update(message.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Turns array of bytes into string
     *
     * @param buf
     *            Array of bytes to convert to hex string
     * @return Generated hex string
     */
    public static String byteArr2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if ((buf[i] & 0xff) < 0x10)
                sb.append("0");

            sb.append(Long.toString(buf[i] & 0xff, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[]
     * buf)互为可逆的转换过程
     *
     * @param src
     *            需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String src) {
        if (src.length() < 1) {
            return null;
        }
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

    private EncryptUtil() {
    }

    @SuppressWarnings("restriction")
	public static String toBase64(String str) {
        if (str == null)
            return null;
        try {
            byte[] b = str.getBytes("utf-8");
            return (new sun.misc.BASE64Encoder()).encode(b);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 加密
    // 解密
    @SuppressWarnings("restriction")
	public static String fromBase64(String str) {
        if (str == null)
            return null;

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(str);
            return new String(b, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
