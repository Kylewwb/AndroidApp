package com.example.youous.androidapp;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by youous on 2016/8/2.
 */
public class User {
    public int Id;
    public String UserName;
    public String PassWord;
    public String Name;
    public String TelNum;
    public String PictureUrl;
    public Bitmap Picture;
    public String Qrcode;
    public long CreateTime;
    public long UpDateTime;
    public int RemTime;
    public int MaxTime = 7200000;

    public User(JSONObject jsonObject){
        try {
            Id = jsonObject.getInt("id");
            Name = jsonObject.getString("name");
            UserName = jsonObject.getString("username");
//            PassWord = jsonObject.getString("password");
            TelNum = jsonObject.getString("telnum");
            PictureUrl = jsonObject.getString("picture");
            Qrcode = jsonObject.getString("qrcode");
            CreateTime = jsonObject.getLong("created");//jsonObject.getString("created");

//            SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//            String retStrFormatNowDate = sdFormatter.format(CreateTime);
//            String retStrFormatNowDate2 = sdFormatter.format(System.currentTimeMillis());
            Log.v("yououaaa",UserName+Qrcode);
//            UpDateTime = jsonObject.getLong("updated");

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.v("yououaaaKKKK",Id+" "+UserName +" "+PassWord+" "+Name+" "+TelNum+" "+PictureUrl+" "+Qrcode+" "+CreateTime+" "+UpDateTime);
    }
    public long getReTime(){//获得剩余时间
        return CreateTime;
    }
    public  Long getReTimeH(){
        return  (getReTime() % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)+8;//移动8个小时时差
    }
    public  Long getReTimeM(){
        return  (getReTime() % (1000 * 60 * 60)) / (1000 * 60);
    }
    public  Long getReTimeS(){
        return  (getReTime() % (1000 * 60)) / 1000;
    }
}
