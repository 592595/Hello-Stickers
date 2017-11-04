package com.iheshulin.yourname.util;

import com.iheshulin.yourname.bean.CheckCode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * Created by HeShulin on 2017/5/19.
 */
public class Message {

    //短信验证码
    private MD5 md5=MD5.getMd5();
    private String username="yanyongjie";
    private String password=md5.getMd5("15296603340yyjqq");
    private GetCheckCode getCheckCode=new GetCheckCode();
    private final OkHttpClient client = new OkHttpClient();
    //phonenum需要发送短信的电话号
    public String sendMessage(String phonenum) throws IOException {
        int len=6;
        String checkcode=getCheckCode.getCheckCode(len);
        String content="【你的名字】，您的验证码为" + checkcode + "在3分钟内有效，请勿泄露验证码给他人";
        String url = "http://www.smsbao.com/sms?u=" + username;
        url = url + "&p=" + password;
        url = url + "&m=";
        url = url + phonenum;
        url = url + "&c=" + content;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(request);
        return checkcode;
    }
}
