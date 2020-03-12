package com.mtnz.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

public class MD5 {

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}
	
	  /**
     * 获取MD5加密后的字符串
     * @param str 明文
     * @return 加密后的字符串
     * @throws Exception 
     */
    public static String getMD5(String str) throws Exception {
        /** 创建MD5加密对象 */
        MessageDigest md5 = MessageDigest.getInstance("MD5"); 
        /** 进行加密 */
        md5.update(str.getBytes());
        /** 获取加密后的字节数组 */
        byte[] md5Bytes = md5.digest();
        String res = "";
        for (int i = 0; i < md5Bytes.length; i++){
            int temp = md5Bytes[i] & 0xFF;
            if (temp <= 0XF){ // 转化成十六进制不够两位，前面加零
                res += "0";
            }
            res += Integer.toHexString(temp);
        }
        return res;
    }
    
    public static String md51(byte[] bys) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(bys);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
    
    public static String getMd51(byte[] buffer) throws NoSuchAlgorithmException{
        String s  = null;
        char hexDigist[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(buffer);
        byte[] datas = md.digest(); //16个字节的长整数
        char[] str = new char[2*16];
        int k = 0;
        for(int i=0;i<16;i++){
          byte b   = datas[i];
          str[k++] = hexDigist[b>>>4 & 0xf];//高4位
          str[k++] = hexDigist[b & 0xf];//低4位
        }
        s = new String(str);
        return s;
      }

    public static String md51(String txt) {
        try{
             MessageDigest md = MessageDigest.getInstance("MD5");
             md.update(txt.getBytes("GBK"));    //问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
             StringBuffer buf=new StringBuffer();            
             for(byte b:md.digest()){
                  buf.append(String.format("%02x", b&0xff));        
             }
            return  buf.toString();
          }catch( Exception e ){
              e.printStackTrace(); 

              return null;
           } 
   }
    
    // 可逆的加密算法   
    public   static  String KL(String inStr) {  
     // String s = new String(inStr);   
     char [] a = inStr.toCharArray();  
     for  ( int  i =  0 ; i < a.length; i++) {  
      a[i] = (char ) (a[i] ^  't' );  
     }  
     String s = new  String(a);  
     return  s;  
    }
    
    // 加密后解密   
    public   static  String JM(String inStr) {  
     char [] a = inStr.toCharArray();  
     for  ( int  i =  0 ; i < a.length; i++) {  
      a[i] = (char ) (a[i] ^  't' );  
     }  
     String k = new  String(a);  
     return  k;  
    }  
    public   static  String MD5(String inStr) {  
    	  MessageDigest md5 = null ;  
    	  try  {  
    	   md5 = MessageDigest.getInstance("MD5" );  
    	  } catch  (Exception e) {  
    	   System.out.println(e.toString());  
    	   e.printStackTrace();  
    	   return   "" ;  
    	  }  
    	  char [] charArray = inStr.toCharArray();  
    	  byte [] byteArray =  new   byte [charArray.length];  
    	  
    	  for  ( int  i =  0 ; i < charArray.length; i++)  
    	   byteArray[i] = (byte ) charArray[i];  
    	  
    	  byte [] md5Bytes = md5.digest(byteArray);  
    	  
    	  StringBuffer hexValue = new  StringBuffer();  
    	  
    	  for  ( int  i =  0 ; i < md5Bytes.length; i++) {  
    	   int  val = (( int ) md5Bytes[i]) &  0xff ;  
    	   if  (val <  16 )  
    	    hexValue.append("0" );  
    	   hexValue.append(Integer.toHexString(val));  
    	  }  
    	  
    	  return  hexValue.toString();  
    	 }  
    
	public static void main(String[] args) throws Exception {
		System.err.println("ef65b6df4f4389f0292cf02e1cd1dfad");
		System.err.println(md5("asdas-asdaskjslfklsasdf-dklfgkdjsgjdfljgldf-asdjaksldjaslkdas-dsfsfasd18736083059"));
	}
}
