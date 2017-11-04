package com.iheshulin.yourname.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iheshulin.baiduimage.Crawler;
import com.iheshulin.baiduspeech.IbmUtil;
import com.iheshulin.baiduspeech.Recognition;
import com.iheshulin.baiduspeech.TransUtil;
import com.iheshulin.yourname.bean.CheckCode;
import com.iheshulin.yourname.bean.User;
import com.iheshulin.yourname.util.MD5;
import com.iheshulin.yourname.util.Message;
import com.iheshulin.yourname.util.UploadFile;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CrossOriginFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HeShulin on 2017/5/28.
 */

@IocBean
public class RegisterController {
    private Log log = Logs.get();
    private MD5 md5=MD5.getMd5();
    public static String getURLContent(String urlStr) {
        /** 网络的url地址 */
        URL url = null;
        /** http连接 */
        HttpURLConnection httpConn = null;
        /**//** 输入流 */
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try{
            url = new URL(urlStr);
            in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") );
            String str = null;
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ex) {

        } finally{
            try{
                if(in!=null) {
                    in.close();
                }
            }catch(IOException ex) {
            }
        }
        String result =sb.toString();
        System.out.println(result);
        return result;
    }

    private String handleText(String text){


        try {
            //百度翻译API调用方式
            TransUtil api = new TransUtil("20170506000046495", "2KdGsc4xYbCh9mkrQdNL");

            String translatedText = api.getTransResult(text, "auto", "en");

            JSONObject data=JSONObject.parseObject(translatedText);
            String result=data.getJSONArray("trans_result").getJSONObject(0).getString("dst");

            //情感识别调用方式


            System.out.println("识别英文:"+result);

            String moodText=null;
            try{
                IbmUtil ibmUtil = new IbmUtil();
                moodText= ibmUtil.useMe(result);
            }catch (Exception e){

                return "false";

            }


            JSONObject moodJson = JSONObject.parseObject(moodText);


            JSONArray tones = moodJson.getJSONObject("document_tone").getJSONArray("tone_categories").getJSONObject(0).getJSONArray("tones");

            JSONObject mytone = new JSONObject();
            mytone.put("score", "0");
            mytone.put("tone_id", "anger");
            mytone.put("tone_name", "Anger");

            for (int i = 0; i < tones.size(); i++) {
                if (tones.getJSONObject(i).getDouble("score") >= mytone.getDouble("score")) {
                    mytone = tones.getJSONObject(i);
                }
            }

            String toneKeyWord = mytone.getString("tone_name");


            String toneKeyWordText = api.getTransResult(toneKeyWord, "auto", "zh");

            String toneResult = JSONObject.parseObject(toneKeyWordText).getJSONArray("trans_result").getJSONObject(0).getString("dst");

            System.out.println("心情:" + toneResult);


            JSONObject keyWordJson = JSONObject.parseObject(KeyWords.getKeyWords(text));

            String word = ""+text.charAt(1)+text.charAt(2);

            String endResult = word + toneResult+"表情包";

            Crawler crawler = new Crawler();
            try {
                crawler.pullPhoto(endResult);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return crawler.getPhotourl();

        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }

    }


    @Inject
    Dao dao;

    @Ok("json")
    @Fail("http:500")
    @At("reigister1")
    @Filters(@By(type=CrossOriginFilter.class))
    public Object register1(@Param("text")String text){

        System.out.println(text);
        try{
            NutMap re = new NutMap();

            re.put("data",handleText(text));
            System.out.println(handleText(text));
            re.put("success",true);
            re.put("msg","操作成功");
            return re;



        }catch (Exception e){
            NutMap re = new NutMap();
            re.put("success", false);
            re.put("msg", "error in register");
            return re;
        }

    }

    @Ok("json")
    @Fail("http:500")
    @At("reigister2")
    @GET
    @Filters(@By(type=CrossOriginFilter.class))
    public Object register2(@Param("audioUrl")String audioUrl){
        try{
            NutMap re = new NutMap();
            System.out.println(audioUrl);
            URL url = new URL(audioUrl);
            InputStream in = url.openStream();
            File dir = new File("E://");
            File file = new File(dir,"test.wav");
            FileOutputStream out = new FileOutputStream(file);
            Streams.copy(in, out, true);
            String text=null;
            try {
                text= Recognition.method3("E://test.wav");
                System.out.println("text"+text);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (text!=null){
                re.put("data",handleText(text));
            }

            re.put("data",true);
            re.put("success",true);
            re.put("msg","操作成功");
            return re;


        }catch (Exception e){
            NutMap re = new NutMap();
            re.put("success", false);
            re.put("msg", "error in register");
            return re;
        }

    }

    @Ok("json")
    @Fail("http:500")
    @At("reigister3")
    @GET
    @Filters(@By(type=CrossOriginFilter.class))
    public Object register3(@Param("videoUrl")String videoUrl){


        try{
            URL url = new URL(videoUrl);
            InputStream in = url.openStream();
            File dir = new File("E:\\python\\change_face\\");
            File file = new File(dir,"test.webm");
            FileOutputStream out = new FileOutputStream(file);
            Streams.copy(in, out, true);

            NutMap re = new NutMap();
            Runtime.getRuntime().exec("cmd /k start python main.py",null,new File("E:\\python\\change_face\\"));
            String tmp2  =null;
            while(true){
                try{
                    File tmp = new File("E:\\python\\change_face\\gif.gif");
                    if(tmp.exists()){
                        UploadFile up = new UploadFile();
                        tmp2  = up.uploadHeadPhoto("E:\\python\\change_face\\gif.gif");
                        tmp.delete();
                        break;
                    }
                }catch (Exception E){
                    E.printStackTrace();
                }


            }
            re.put("data",tmp2);
            re.put("success",true);
            re.put("msg","操作成功");
            return re;


        }catch (Exception e){
            NutMap re = new NutMap();
            re.put("success", false);
            re.put("msg", "error in register");
            return re;
        }

    }
}
