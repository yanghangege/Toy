package com.test.BaiduSpeak.SpeakTest;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class Speak {
    public static void main(String[] args) {
        String str = "你好，我是java小新人！";

        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");

        Dispatch sapo = sap.getObject();
        try {
            //音量 0-100
            sap.setProperty("Volume",new Variant(50));
            //语音朗读速度 - 10 到 +10
            sap.setProperty("Rate",new Variant(0));
            //执行朗读
            Dispatch.call(sapo,"Speak",new Variant(str));
        }catch(Exception exception){
            exception.printStackTrace();
        }finally{
            sapo.safeRelease();
            sap.safeRelease();
        }
    }

}
