/* 
 * @ProjectName VideoGoJar
 * @Copyright HangZhou Hikvision System Technology Co.,Ltd. All Right Reserved
 * 
 * @FileName EzvizApplication.java
 * @Description 这里对文件进行描述
 * 
 * @author chenxingyf1
 * @data 2014-7-12
 * 
 * @note 这里写本文件的详细功能描述和注释
 * @note 历史记录
 * 
 * @warning 这里写本文件的相关警告
 */
package com.example.youous.androidapp;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.videogo.constant.Config;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZCameraInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 自定义应用
 * 
 * @author xiaxingsuo
 * 
 */
public class EzvizApplication extends Application {
	// 开放平台申请的APP key & secret key
	// open
    public static String APP_KEY = "7d29c71eae264e17a77c1617d29d8804"; // 2015/10/29

    public static List<EZCameraInfo> result;
	@Override
	public void onCreate() {
		super.onCreate();

        Config.LOGGING = true;
        EZOpenSDK.initLib(this, "5cdc918982cf447e986c04743e81948b", "");

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
            }
        });


        new Thread(){
            public void run() {
                //申请accessToken
                String aaaaaaaa = "https://open.ys7.com/api/lapp/token/get"+"?appKey=5cdc918982cf447e986c04743e81948b&appSecret=105a96f6a58f43163f6a6b33b72e0423";
                String response = HttpRequest.post(aaaaaaaa).body();
                try {
                    JSONObject jsonObj = new JSONObject(response).getJSONObject("data");
                    String accesstoken =  jsonObj.getString("accessToken");
                    EZOpenSDK.getInstance().setAccessToken(accesstoken);
                    result = null;
                    try {
                        result = EZOpenSDK.getInstance().getCameraList(0, 20);
//                        Log.e("yououaaa","YYYYYYYYYYYYYYY  "+result.size());
                    } catch (BaseException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException ex) {

                }
            }
        }.start();
	}
}
