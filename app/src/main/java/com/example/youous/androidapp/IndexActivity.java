package com.example.youous.androidapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;


/**
 * Created by youous on 2016/8/12.
 */
public class IndexActivity extends AppCompatActivity implements View.OnClickListener{
    public static IndexActivity indAct;
    ImageView exit,tuoguan,jiankong;
    Boolean isBB;
    LinearLayout saoma;
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        indAct = this;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        exit = (ImageView)findViewById(R.id.index_exit);
        exit.setOnClickListener(this);
        tuoguan = (ImageView)findViewById(R.id.index_tuoguan);
        tuoguan.setOnClickListener(this);
        jiankong  = (ImageView)findViewById(R.id.index_jiankong);
        jiankong.setOnClickListener(this);

        saoma= (LinearLayout) findViewById(R.id.main_saoma);
        saoma.setOnClickListener(this);

        textview = (TextView)findViewById(R.id.index_time);
        textview.setText(Data.GetStringData());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.index_exit:
                startActivity(new Intent(IndexActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.index_tuoguan:
                startActivity(new Intent(IndexActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.main_saoma:
                Data.GetUser();
                startActivityForResult(new Intent(IndexActivity.this, CaptureActivity.class), 0);
                break;
            case R.id.index_jiankong:
                startActivity(new Intent(IndexActivity.this,RealListActivity.class));
                finish();
                break;
        }
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
                startActivity(new Intent(IndexActivity.this,AddActivity.class));
                finish();
                return;
            }

            int id = Data.SearchQUserId(result);
            if(id==-1){
                Toast.makeText(this, "用户不存在", Toast.LENGTH_LONG).show();

            }else{
                Data.GetUserId = id;
                startActivity(new Intent(IndexActivity.this,GetActivity.class));
                finish();
            }

        }
    }
}


