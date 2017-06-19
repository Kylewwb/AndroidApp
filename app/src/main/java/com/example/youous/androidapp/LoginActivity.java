package com.example.youous.androidapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by youous on 2016/7/29.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    //用户名密码
    public EditText et_name,et_pass;
    public Button bt_Login;
    public  ImageView iv_top,lg_clear,lg_clear2;
    public  static LoginActivity loginact ;
    public CheckBox checkbox;
    Bitmap bitmao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginact =this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
////            window.setNavigationBarColor(Color.TRANSPARENT);
//        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        init();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }


//        new Thread(){
//            public void run() {
//                Log.v("yououaaa",Data.Http_ADD_Url+"?username="+"11111"+"&name="+"ffff"+"&telnum="+"aaaa"+"&qrcode="+"asdf"+"&picture="+ "ffffff" +"&time="+7200000);
//                 HttpRequest.get(Data.Http_ADD_Url+"?username="+"11111"+"&name="+"ffff"+"&telnum="+"aaaa"+"&qrcode="+"asdf"+"&picture="+ "ffffff" +"&time="+7200000).body();
//
//            }
//        }.start();
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



    //初始化
    protected void init(){
        et_name = (EditText) findViewById(R.id.login_name);

        Drawable drawable=getResources().getDrawable(R.drawable.login_name);
        drawable.setBounds(0,0,70,70);
        et_name.setCompoundDrawables(drawable,null,null,null);
        et_name.addTextChangedListener(textWatcher);

        et_pass = (EditText) findViewById(R.id.login_password);
        Drawable drawable2=getResources().getDrawable(R.drawable.login_password);
        drawable2.setBounds(0,0,70,70);
        et_pass.setCompoundDrawables(drawable2,null,null,null);
        et_pass.addTextChangedListener(textWatcher2);

        bt_Login = (Button) findViewById(R.id.login_button);
        bt_Login.setOnClickListener(this);
        iv_top = (ImageView) findViewById(R.id.login_topimage);
        lg_clear = (ImageView) findViewById(R.id.login_clear);
        lg_clear.setOnClickListener(this);
        lg_clear2 = (ImageView) findViewById(R.id.login_clear2);
        lg_clear2.setOnClickListener(this);

        lg_clear.setVisibility(View.INVISIBLE);
        lg_clear2.setVisibility(View.INVISIBLE);

        et_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    //获得焦点处理
                    if(!TextUtils.isEmpty(et_name.getText())){
                        lg_clear.setVisibility(View.VISIBLE);
                    }

                }
                else {
                    //失去焦点处理
                    lg_clear.setVisibility(View.INVISIBLE);
                }
            }
        });

        et_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    //获得焦点处理
                    if(!TextUtils.isEmpty(et_pass.getText())){
                        lg_clear2.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    //失去焦点处理
                    lg_clear2.setVisibility(View.INVISIBLE);
                }
            }
        });
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login_button:
//                Log.v("yououaaa","FFFFFFFFFFFFFFFFFFF");
                   login();

//                new Thread(){
//                    public void run() {
//                        Map<String, String> data = new HashMap<String, String>();
//                        data.put("name", "张三");
//                        data.put("username", "admin");
//                        data.put("telnum", "15621075876");
//                        data.put("qrcode", "12345678");
//                        data.put("created", "1470273664000");
//                        data.put("picture", "http://192.168.10.105/images/2016/07/07/1467874171815159.jpg");
//                        HttpRequest.post("http://192.168.10.164:8080/info/add").form(data).created();
//
//                    }
//                }.start();
                break;
            case R.id.login_clear:
                et_name.setText(null);
//                et_pass.setText(null);
                break;
            case R.id.login_clear2:
//                et_name.setText(null);
                et_pass.setText(null);
                break;
        }
    }
    //登录
    public void login(){
        //发送 GET 请求
        new Thread(){
                public void run() {

                String response = HttpRequest.get(Data.Http_Login_Url+"?username="+et_name.getText()+"&password="+et_pass.getText()).body();

                if (response.equals("0")){
                    loginact.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(loginact, "用户名密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (response.equals("1")){

                    startActivity(new Intent(LoginActivity.this,IndexActivity.class));
                    finish();
                }else {
                    loginact.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(loginact, "网络异常", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }.start();
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.d("TAG","afterTextChanged--------------->");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.d("TAG","beforeTextChanged--------------->");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            Log.d("TAG","onTextChanged--------------->");
            if(!TextUtils.isEmpty(et_name.getText())){
                lg_clear.setVisibility(View.VISIBLE);
            }else {
                lg_clear.setVisibility(View.INVISIBLE);
            }
        }
    };
    private TextWatcher textWatcher2 = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.d("TAG","afterTextChanged--------------->");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.d("TAG","beforeTextChanged--------------->");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            Log.d("TAG","onTextChanged--------------->");
            if(!TextUtils.isEmpty(et_pass.getText())){
                lg_clear2.setVisibility(View.VISIBLE);
            }else {
                lg_clear2.setVisibility(View.INVISIBLE);
            }
        }
    };
}