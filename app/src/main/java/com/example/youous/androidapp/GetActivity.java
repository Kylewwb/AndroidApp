package com.example.youous.androidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by youous on 2016/8/2.
 */
public class GetActivity extends AppCompatActivity implements View.OnClickListener, Runnable {


    public static GetActivity getact;
    ImageButton GetExit;
    Button GetButton1, GetButton2;
    ImageView GetView;
    Bitmap bitmap;
    TextView GetName, GetTel, GetTimeHH1,GetTimeHH2, GetTimeMM1,GetTimeMM2, GetTimess1,GetTimess2,GetTop;
    Boolean isBB;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public long  ctime;
    public long  maxtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        getact = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_actitity);
        isBB = true;
        GetExit = (ImageButton) findViewById(R.id.getexit);
        GetExit.setOnClickListener(this);
        GetButton1 = (Button) findViewById(R.id.getbutton1);
        GetButton1.setOnClickListener(this);
        Drawable drawable=getResources().getDrawable(R.drawable.tel);
        drawable.setBounds(0,0,130,130);
        GetButton1.setCompoundDrawables(drawable,null,null,null);

        GetButton2 = (Button) findViewById(R.id.getbutton2);
        GetButton2.setOnClickListener(this);
        Drawable drawable2=getResources().getDrawable(R.drawable.main_img3);
        drawable2.setBounds(0,0,130,130);
        GetButton2.setCompoundDrawables(drawable2,null,null,null);


        GetView = (ImageView) findViewById(R.id.getimg);

        GetName = (TextView) findViewById(R.id.getname);
        GetName.setText(Data.vUser.get(Data.GetUserId).Name);
        GetTel = (TextView) findViewById(R.id.gettelnum);
        GetTel.setText(Data.vUser.get(Data.GetUserId).TelNum);
        GetTimeHH1 = (TextView) findViewById(R.id.gettimehh1);
        GetTimeHH2 = (TextView) findViewById(R.id.gettimehh2);
        GetTimeMM1 = (TextView) findViewById(R.id.gettimemm1);
        GetTimeMM2 = (TextView) findViewById(R.id.gettimemm2);
        GetTimess1 = (TextView) findViewById(R.id.gettimess1);
        GetTimess2 = (TextView) findViewById(R.id.gettimess2);
        ctime = Data.vUser.get(Data.GetUserId).CreateTime;
        maxtime = Data.vUser.get(Data.GetUserId).MaxTime;
        GetTop = (TextView)findViewById(R.id.get_toptv);
        GetTop.setText( Data.vUser.get(Data.GetUserId).Name+"详情");
        new Thread() {
            public void run() {
                //加载图片
                String url = Data.vUser.get(Data.GetUserId).PictureUrl;
                Bitmap bbb =BitmapFactory.decodeStream(HttpRequest.get(url).stream());
                if(bbb==null){
                    return;
                }
                bitmap = Data.zoomImage(bbb, 1200, 865);
                demoHandler.sendEmptyMessage(111);
            }
        }.start();

        new Thread(this).start();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    Handler demoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GetView.setImageBitmap(bitmap);

        }
    };
    Handler demoHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isBB == false) {
                return;
            }
            if(Data.GetUserId>=Data.vUser.size()){
                return;
            }
            long cttime = ctime;
            long ctime = System.currentTimeMillis();
            long aa = maxtime - (ctime - cttime);
            GetTimeHH1.setText(Data.getTimeHHl(aa)/10+"");
            GetTimeHH2.setText(Data.getTimeHHl(aa)%10+"");
            if(Data.getTimeHHl(aa)>0){
                GetTimeHH1.setBackgroundColor(0xffff8c00);
                GetTimeHH2.setBackgroundColor(0xffff8c00);
            }else {
                GetTimeHH1.setBackgroundColor(0xff666666);
                GetTimeHH2.setBackgroundColor(0xff666666);
            }

            GetTimeMM1.setText(Data.getTimeMMl(aa)/10+"");
            GetTimeMM2.setText(Data.getTimeMMl(aa)%10+"");
            if(Data.getTimeMMl(aa)>0||Data.getTimeHHl(aa)>0){
                GetTimeMM1.setBackgroundColor(0xffff8c00);
                GetTimeMM2.setBackgroundColor(0xffff8c00);
            }else {
                GetTimeMM1.setBackgroundColor(0xff666666);
                GetTimeMM2.setBackgroundColor(0xff666666);
            }


            GetTimess1.setText(Data.getTimeSSl(aa)/10+"");
            GetTimess2.setText(Data.getTimeSSl(aa)%10+"");
            if(Data.getTimeSSl(aa)>0||Data.getTimeMMl(aa)>0||Data.getTimeHHl(aa)>0){
                GetTimess1.setBackgroundColor(0xffff8c00);
                GetTimess2.setBackgroundColor(0xffff8c00);
            }else {
                GetTimess1.setBackgroundColor(0xff666666);
                GetTimess2.setBackgroundColor(0xff666666);
            }


        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getexit:

                startActivity(new Intent(GetActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.getbutton1:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + GetTel.getText());
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.getbutton2:
                new AlertDialog.Builder(this).setTitle("提示").setMessage("确定接走宝宝吗？")
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                isBB = false;
                                new Thread() {
                                    public void run() {

                                        String response = HttpRequest.delete(Data.Http_Delete_Url + "/" + Data.vUser.get(Data.GetUserId).TelNum).body();
                                        Log.e("yououaaa","ff   "+Data.Http_Delete_Url + "/" + Data.vUser.get(Data.GetUserId).TelNum);
                                        startActivity(new Intent(GetActivity.this, MainActivity.class));
                                        finish();

                                    }
                                }.start();
//                                Toast.makeText(this, "宝宝接走了", Toast.LENGTH_SHORT).show();

                            }
                        }).show();

                break;
        }
    }




    @Override
    public void run() {

        while (isBB) {

            demoHandler2.sendEmptyMessage(111);
//                sleep();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}