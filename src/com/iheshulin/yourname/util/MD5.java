package com.iheshulin.yourname.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Created by HeShulin on 2017/5/17.
 */
public class MD5 {
    //单例模式
    static MD5 md5=new MD5();
    private MD5() {
    }
    public static MD5 getMd5() {
        return md5;
    }

    //获取MD5字符串
    public static String getMd5(String plainText) {

        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }



        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buf.toString();
    }

    private static final String salt = "heshulin@imudges.com";

    public static String encryptTimeStamp(String ts){
        return getMd5(getMd5(ts)+"&"+salt);
    }
}
