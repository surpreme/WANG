package com.liziyang.dall.com.chat.liziyang;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;


public class HttpUtils {
    //实现消息发送与接受的类
    //这俩从机器人第三方平台获得
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY ="627ef0a6d6b14f8391c41c93752dcaec";

    public static ChatMessage sendMessage(String msg) {
        ChatMessage chatMessage = new ChatMessage ();
        //服务器返回的数据
        String jsonRes = doGet ( msg );
        Gson gson = new Gson ();
        Result result = null;
        try {
            result = gson.fromJson ( jsonRes , Result.class );
            chatMessage.setMsg ( result.getText () );
        } catch (JsonSyntaxException e) {

            chatMessage.setMsg ( "服务器繁忙 请稍候再试" );
        }
        //设置返回时间
        chatMessage.setDate ( new Date () );
        //返回枚举种类
        chatMessage.setType ( ChatMessage.Type.INCOMING);


        return chatMessage;
    }

    public static String doGet(String msg) {
        String result = "";
        //setParams这里是自己定义的方法
        //用户传入一个message 调用setParams
        String url = setParams ( msg );
        InputStream is = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            //写入这个方法  try方法自动补全
            java.net.URL urlNet = new java.net.URL ( url );
            //添加catch语句避免异常
            //打开一个连结
            HttpURLConnection connection = (HttpURLConnection) urlNet.openConnection ();
            //设置一些获取工具的参数
            //5秒
            connection.setReadTimeout ( 5 * 1000 );
            connection.setConnectTimeout ( 5 * 1000 );
            //设置请求方式
            connection.setRequestMethod ( "GET" );
            //得到服务器返回流  InputStream is
            is = connection.getInputStream ();
            int len = -1;
            //声明缓冲区为128个字节
            byte[] buf = new byte[ 128 ];
            //byteArrayOutputStream
            byteArrayOutputStream = new ByteArrayOutputStream ();
            //一直读到等于-1结束 否则一直循环
            while ((len = is.read ( buf )) != -1) {
                byteArrayOutputStream.write ( buf , 0 , len );
            }
            //读到服务器数据本地
            byteArrayOutputStream.flush ();
            result = new String ( byteArrayOutputStream.toByteArray () );
        } catch (MalformedURLException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        } finally {
            //释放所有资源
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
                if (is != null) {
                    try {
                        is.close ();
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                }
            }


        }

        return result;
    }

    private static String setParams(String msg) {
        //这个格式是第三方的规范 根据实际而定
        String url = "";
        try {
            url = URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode ( msg ,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace ();
        }
        return url;
    }
}

