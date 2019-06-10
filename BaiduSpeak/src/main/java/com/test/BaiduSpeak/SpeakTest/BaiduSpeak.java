package com.test.BaiduSpeak.SpeakTest;


import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;

public class BaiduSpeak {
    //设置APPID/AK/SK，这三个参数是需要我们去百度AI平台申请的（也就是上面说的那三个字符串）
    public static final String APP_ID = "16472923";
    public static final String API_KEY = "yVPjSSTdAcDTexuwEk1VKP77";
    public static final String SECRET_KEY = "tCAX8QBGgHrODPlMuwBhwhFc6AdqiISW";

    //readFile是我们的txt文档，writeFile是输出的MP3格式
    public static String readFile = "E:\\TestSpeak.txt";
    public static String writeFile = "E:\\output.mp3";


    public static void main(String[] args) {
        //可以直接输入字符串也行，内容比较多的话还是用txt文档比较好一点
        //convertMP3("你好！我是百度AI智能,java小新人，很高兴和你见面，我们一定能成为很好的朋友的");


        //调用readToString方法将一个txt文档中的数据读取出来变成一个字符串
        String string = readToString(readFile);
        //将这个字符串用百度AI读一下输出MP3格式
        convertMP3(string);

    }
    public static void convertMP3(String str) {
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数，就是超时时间
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 设置一些可选参数
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "5");//语速，取值0-9，默认为5中语速      非必选
        options.put("pit", "5");//音调，取值0-9，默认为5中语调      非必选
        options.put("per", "4");//发音人选择, 0为女声，1为男声，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女 非必选

        //百度AI开始读取传入的str字符串
        TtsResponse res = client.synthesis(str, "zh", 1, options);

        //服务器返回的内容，合成成功时为null,失败时包含error_no等信息
        JSONObject result = res.getResult();
        if (result != null) {
            System.out.printf("error：" + result.toString()+"----------");
            return;
        }
        //生成的音频数据
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                //将生成的音频输出到指定位置
                Util.writeBytesToFileSystem(data, writeFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (res1 != null) {
            System.out.println(res1.toString());
        }
    }

    //这个方法就是根据输入的文件路径，读取该文件内容返回一个很长的字符串，由于txt是gbk编码，所以我们变成字符串的时候也要用gbk
    //其实就是最基本的流操作
    public static String readToString(String fileName) {
        String encoding = "gbk";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];

        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

}
