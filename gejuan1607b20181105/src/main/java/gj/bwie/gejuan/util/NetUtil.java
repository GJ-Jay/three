package gj.bwie.gejuan.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetUtil {
    public static String getJson(String urlString) {
        //请求json数据
        try {
            //url 统一资源定位符  获取请求路径
            URL url = new URL(urlString);
            //网络请求用HttpURLConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //获取响应码
            int responseCode = httpURLConnection.getResponseCode();
            //设置连接网络超时时间
            httpURLConnection.setConnectTimeout(8000);
            //判断请求是否成功
            if(responseCode==200){
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                //拼接字符串
                String temp ="";
                StringBuilder stringBuilder = new StringBuilder();
                if((temp=bufferedReader.readLine())!=null){
                    stringBuilder.append(temp);
                }
                return stringBuilder.toString();
            }else{
                //请求失败 打印log
                Log.e("gj","json请求失败："+responseCode);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
