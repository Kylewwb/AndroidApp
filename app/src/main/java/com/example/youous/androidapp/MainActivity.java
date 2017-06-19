package com.example.youous.androidapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static MainActivity mmmm;
    Button Exit, Add, Get,Search;
    TextView Num;
    EditText SearchEdit;
    boolean isok;

    @InjectView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        mmmm = this;
//        Data.GetUser();
        isok = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ButterKnife.inject(mmmm);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mmmm));
        mRecyclerView.setAdapter(new HomeAdapter());
        Exit = (Button) findViewById(R.id.main_exit);
        Exit.setOnClickListener(mmmm);
        Add = (Button) findViewById(R.id.main_add);
        Add.setOnClickListener(mmmm);
        Drawable drawable3=getResources().getDrawable(R.drawable.main_img4);
        drawable3.setBounds(0,0,130,130);
        Add.setCompoundDrawables(drawable3,null,null,null);


        Get = (Button) findViewById(R.id.main_get);
        Get.setOnClickListener(mmmm);
        Drawable drawable4=getResources().getDrawable(R.drawable.er);
        drawable4.setBounds(0,0,130,130);
        Get.setCompoundDrawables(drawable4,null,null,null);


        Num = (TextView) findViewById(R.id.main_num);
        Num.setText("" + Data.vUser.size());
        Search= (Button) findViewById(R.id.main_search);
        Search.setOnClickListener(mmmm);
        SearchEdit = (EditText) findViewById(R.id.main_sousuoeeee);
        Drawable drawable=getResources().getDrawable(R.drawable.main_sousuo);
        drawable.setBounds(0,0,60,60);
        SearchEdit.setCompoundDrawables(drawable,null,null,null);

        TextView xinxi = (TextView)findViewById(R.id.main_xinxi);
        Drawable drawable2=getResources().getDrawable(R.drawable.icon01);
        drawable2.setBounds(0,0,60,60);
        xinxi.setCompoundDrawables(drawable2,null,null,null);




        new Thread(runnable).start();
        new Thread(){
            public void run() {
                String response = HttpRequest.get(Data.Http_GetUser_Url).body();
                Log.v("yououaaa","用户信息11："+response);
                JSONArray jsonArray = null;
                Data.vUser.removeAllElements();//清空用户信息
                try {
                    jsonArray = new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        User user = new User(jsonObject);
                        Data.vUser.add(user);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mmmm.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mRecyclerView.setAdapter(new HomeAdapter());
                    }
                });

            }
        }.start();
//        ButterKnife.inject(this);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(new HomeAdapter());
//        Exit = (Button) findViewById(R.id.main_exit);
//        Exit.setOnClickListener(this);
//        Add = (Button) findViewById(R.id.main_add);
//        Add.setOnClickListener(this);
//        Get = (Button) findViewById(R.id.main_get);
//        Get.setOnClickListener(this);
//        Num = (TextView) findViewById(R.id.main_num);
//        Num.setText("" + Data.vUser.size());
//        Search= (Button) findViewById(R.id.main_search);
//        Search.setOnClickListener(this);
//        SearchEdit = (EditText) findViewById(R.id.main_sousuoeeee);
//        new Thread(runnable).start();

    }

//    public void init(){
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(new HomeAdapter());
//        Exit = (Button) findViewById(R.id.main_exit);
//        Exit.setOnClickListener(this);
//        Add = (Button) findViewById(R.id.main_add);
//        Add.setOnClickListener(this);
//        Get = (Button) findViewById(R.id.main_get);
//        Get.setOnClickListener(this);
//        Num = (TextView) findViewById(R.id.main_num);
//        Num.setText("" + Data.vUser.size());
//        Search= (Button) findViewById(R.id.main_search);
//        Search.setOnClickListener(this);
//        SearchEdit = (EditText) findViewById(R.id.main_sousuoeeee);
//        new Thread(runnable).start();
//    }

    Handler demoHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==111){
                Num.setText("" + Data.vUser.size());


            }
            postDelayed(runnable, 200);
        }
    };

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            demoHandler.sendEmptyMessage(111);
        }
    };

    @Override
    public void onClick(View view) {
        if(isok == true){
            isok=false;
            return;
        }
        isok=true;
        switch (view.getId()){
            case R.id.main_exit:
                startActivity(new Intent(MainActivity.this,IndexActivity.class));
                this.finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            case R.id.main_add:
                startActivity(new Intent(MainActivity.this,AddActivity.class));
                finish();
                break;
            case R.id.main_get:
                Data.GetUser();
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
                break;
            case R.id.main_search:
//                Data.GetUser();
//                int id = Data.SearchUserId(SearchEdit.getText()+"");
//                if(id==-1){
//                    Toast.makeText(this, "用户不存在", Toast.LENGTH_LONG).show();
//                }else{
//                    Data.GetUserId = id;
//                    startActivity(new Intent(MainActivity.this,GetActivity.class));
//                    finish();
//                }
                new Thread(){
                    public void run() {
                        String response = HttpRequest.get(Data.Http_GetUser_Url).body();
                        Log.v("yououaaa","用户信息11："+response);
                        JSONArray jsonArray = null;
                        Data.vUser.removeAllElements();//清空用户信息
                        try {
                            jsonArray = new JSONArray(response);

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                User user = new User(jsonObject);
                                Data.vUser.add(user);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        int id = Data.SearchUserId(SearchEdit.getText()+"");

                if(id==-1){
                    mmmm.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mmmm, "用户不存在", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(MainActivity.this,MainActivity.class));
//                            finish();
//                            return;
                            mRecyclerView.setAdapter(new HomeAdapter());
                        }
                    });
                }else{
                    Data.GetUserId = id;
                    startActivity(new Intent(MainActivity.this,GetActivity.class));
                    finish();
                }
                    }

                }.start();
                break;

        }
    }


    public static void toGetAct(){
        mmmm.startActivity(new Intent(MainActivity.mmmm,GetActivity.class));
        mmmm.finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
//            L.d("result: " + result);
//            Toast.makeText(this, "  "+result, Toast.LENGTH_LONG).show();
            //解析字符串
            Data.AddData = Data.getStringArray(result,'@');
            if(Data.AddData[0].equals("新增")){
                Data.isAddData = true;
                startActivity(new Intent(MainActivity.this,AddActivity.class));
                finish();
                return;
            }

            int id = Data.SearchQUserId(result);
            if(id==-1){
                Toast.makeText(this, "用户不存在", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(MainActivity.this,MainActivity.class));
//                finish();
                mRecyclerView.setAdapter(new HomeAdapter());
            }else{
                Data.GetUserId = id;
                startActivity(new Intent(MainActivity.this,GetActivity.class));
                finish();
            }

        }
    }

}
