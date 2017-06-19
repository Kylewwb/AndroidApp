package com.example.youous.androidapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.util.Utils;

import java.util.List;

/**
 * Created by youous on 2016/8/22.
 */
public class RealPlayActivity extends AppCompatActivity implements Handler.Callback ,SurfaceHolder.Callback,View.OnClickListener{

    public static int Id = 0;
    private Handler mHandler = null;
    private SurfaceHolder mRealPlaySh = null;
    EZPlayer mEZPlayer = null;
    private SurfaceView mRealPlaySv = null;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.realplay);


        mHandler = new Handler(this);
        EZOpenSDK mEZOpenSDK = EZOpenSDK.getInstance();
//        mEZPlayer = mEZOpenSDK.createPlayer(this, "be89936250944c3bbae065a54d177fe5");
        final String cameraId = Utils.getCameraId(EzvizApplication.result.get(Id).getCameraId());
        mEZPlayer = mEZOpenSDK.createPlayer(this, cameraId);
        if (mEZPlayer == null)
            return;
        mEZPlayer.setHandler(mHandler);
        mEZPlayer.setSurfaceHold(mRealPlaySh);
        mEZPlayer.startRealPlay();
        mRealPlaySv = (SurfaceView) findViewById(R.id.realplay_sv);
        mRealPlaySv.getHolder().addCallback(this);

        exit = (Button)findViewById(R.id.realplay_exit);
        exit.setOnClickListener(this);

    }


    @Override
    public boolean handleMessage(Message message) {

        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(holder);
        }
        mRealPlaySh = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(null);
        }
        mRealPlaySh = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.realplay_exit:
               mEZPlayer.stopRealPlay();
               startActivity(new Intent(this,RealListActivity.class));
               finish();
                break;
        }
    }




}
