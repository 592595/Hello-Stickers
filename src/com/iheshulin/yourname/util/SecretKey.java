package com.iheshulin.yourname.util;

import java.util.Random;
import com.iheshulin.yourname.util.MD5;

/**
 * Created by HeShulin on 2017/5/17.
 */
public class SecretKey {
    public static String getSecretKey() {
        //获取secretkey
        MD5 md5=MD5.getMd5();
        String src="ZXCVBNMASDFGHJKLQWERTYUIOPzxcvbnmasdfghjklpouiytrewq";
        String random="";
        for(int i=0;i<10;i++)
        {
            int temp=(int)((Math.random()*10000)%52);
            random=random + src.charAt(temp);
        }
        String finnalrandom=md5.getMd5(random);
        return finnalrandom;
    }

}
