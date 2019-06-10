package com.test.BaiduSpeak.SpeakTest;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class JavaSpeak {
    public static void main(String[] args) {
        //指定文件音频输出文件位置！
        String output = "E:\\test.wav";

        ActiveXComponent ax = null;
        String str = "我是java的小新人，我将要将这段音频输出一下";
        try {
            ax = new ActiveXComponent("Sapi.spVoice");

            //运行时输出语音内容
            Dispatch spVoice = ax.getObject();
            //音量 0- 100
            ax.setProperty("Volume", new Variant(100));
            //语音朗读速度 - 10 到+ 10
            ax.setProperty("Rate",new Variant(-3));
            //进行朗读
            Dispatch.call(spVoice,"Speak",new Variant(str));

            //下面是构建文件流把生成语音文件

            ax = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = ax.getObject();

            //设置音频流格式
            Dispatch.put(spAudioFormat,"Type", new Variant(22));
            //设置文件流输出格式
            Dispatch.putRef(spFileStream,"Format",spAudioFormat);
            //调用输出文件流打开方式，在指定位置输出一个.wav文件
            Dispatch.call(spFileStream,"Open",new Variant(output),new Variant(3),new Variant(true));
            //设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            //设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            //设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(-2));
            //开始朗读
            Dispatch.call(spVoice, "Speak", new Variant(str));

            //关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);

            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
