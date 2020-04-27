package com.mtnz.util;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

public class RASTest {

    private static final String src = "45rFg5erFuevVSZ4R7zkxg==";

    private static final String iv = "asdfivh7";
    public static void main(String [] args){
        jdkDES();

    }
    public static byte[] getIv(){
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[8];
        random.nextBytes(iv);
        byte[] ivStr=new IvParameterSpec(iv).getIV();
        return ivStr;
    }

    public static String getKeyDES(String src){
        try {
            //获得KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);//设置为默认值56即可
            //获得KEY对象
/*            SecretKey secrekeyone = keyGenerator.generateKey();
//            byte [] byteskey = secrekeyone.getEncoded();*/
            byte [] byteskey = "m1qaFF3FqSf2xAES52ia3Q".getBytes();


            //KEY转换
            DESKeySpec deskeyspec = new DESKeySpec(byteskey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            Key secerkeytwo = factory.generateSecret(deskeyspec);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());

            //解密
            cipher.init(cipher.DECRYPT_MODE, secerkeytwo,ips);//设置模式为解密
            byte[] a = Base64.getDecoder().decode(src);
            a = cipher.doFinal(a);
            System.out.println("jdkEDS:"+new String(a));
            return new String(a);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void jdkDES(){
        try {
            //获得KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);//设置为默认值56即可
            //获得KEY对象
 //           SecretKey secrekeyone = keyGenerator.generateKey();
//            byte [] byteskey = secrekeyone.getEncoded();
            byte [] byteskey = "m1qaFF3FqSf2xAES52ia3Q".getBytes();


            //KEY转换
            DESKeySpec deskeyspec = new DESKeySpec(byteskey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            Key secerkeytwo = factory.generateSecret(deskeyspec);

            //加密
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            /*cipher.init(cipher.ENCRYPT_MODE, secerkeytwo,ips);//设置模式为加密
            byte[] result = cipher.doFinal(src.getBytes());*/
            /*byte[] a = Base64.getDecoder().decode("ZQDJLFMokz7TT96NCodE9g==");
            String b = new String(a);
            System.out.println(b);
            System.out.println("jdkEDS:"+result.toString());*/

            //解密
            cipher.init(cipher.DECRYPT_MODE, secerkeytwo,ips);//设置模式为解密
            byte[] a = Base64.getDecoder().decode(src);
            a = cipher.doFinal(a);
            System.out.println("jdkEDS:"+new String(a));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
