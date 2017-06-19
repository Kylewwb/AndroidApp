package com.example.youous.androidapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by youous on 2016/8/22.
 */
public class RealListActivity extends AppCompatActivity implements View.OnClickListener {
    public static RealListActivity mmmm;
    Button exit;

    @InjectView(R.id.real_list)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }



        mmmm = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_activity);
        ButterKnife.inject(mmmm);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mmmm));
        mRecyclerView.setLayoutManager((new GridLayoutManager(mmmm,2)));
        mRecyclerView.setAdapter(new RealAdpter());
        exit = (Button)findViewById(R.id.real_exit);
        exit.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.real_exit:
                startActivity(new Intent(RealListActivity.this,IndexActivity.class));
                finish();
                break;
        }
    }


}