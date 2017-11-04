package test;


import com.iheshulin.baiduimage.Crawler;
import com.iheshulin.yourname.util.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by HeShulin on 2017/5/17.
 */
public class tes {
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


//        ArrayList a1 = new ArrayList();
//        a1.add("1");
//        a1.add("2");
//        a1.add("3");
//        System.out.println(a1.get(a1.size()-1));
    public static void main(String arg0[]) throws IOException, InterruptedException {
        Runtime.getRuntime().exec("cmd /k start python main.py",null,new File("E:\\python\\change_face\\"));

    }


}
