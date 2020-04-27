package com.mtnz.util;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 对称加密
 */
public class RASymmetry {

    //加密
    public static String aesEncrypt(String key, String content, byte[] iv) {
        String aesEncrypted = null;
        try {
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ips = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
            byte[] encrypted = cipher.doFinal(content.getBytes("UTF-8"));
//            aesEncrypted=new String(encrypted);
            aesEncrypted=new String(Base64.getEncoder().encode(encrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aesEncrypted;
    }
    //解密
    public static String aesDecrypt(String key, String content, byte[] iv) throws Exception {
        try {
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
//            byte[] encrypted1 = content.getBytes("UTF-8");
            byte[] encrypted1 = Base64.getDecoder().decode(content);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original).trim();
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static byte[] getIv(){
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        byte[] ivStr=new IvParameterSpec(iv).getIV();
        return ivStr;
    }



    public static String getA221(){
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String s = byteToHexString(b);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
        }
        return null;
    }
    /**
     * 二进制byte[]转十六进制string
     */
    public static String byteToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex=Integer.toHexString(bytes[i]);
            if(strHex.length() > 3){
                sb.append(strHex.substring(6));
            } else {
                if(strHex.length() < 2){
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return  sb.toString();
    }

    public static byte[] generateDesKey(int length) throws Exception {
        //实例化
        KeyGenerator kgen = null;
        kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(length);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        //返回密钥的二进制编码
        return skey.getEncoded();
    }

    public static void main(String[] args) {
        byte[] iv=getIv();
        String s=aesEncrypt("677acbe8a07ee5d1ead0a58638f25879","15670635990",iv);
        try {
            String d=aesDecrypt("677acbe8a07ee5d1ead0a58638f25879",s,iv);
            System.out.println(s+"==========="+d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
