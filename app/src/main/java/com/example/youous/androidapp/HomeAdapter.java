package com.example.youous.androidapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by youous on 2016/8/2.
 */
class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.rv_list)
        TextView tv;
        TextView viewtime1,viewtime2;
        TextView button;
        LinearLayout ll;
        long ccc =0;

        public MyViewHolder(View view)
        {
            super(view);
            ButterKnife.inject(itemView);
            tv = (TextView) view.findViewById(R.id.tv_item_name);
//            tv2 = (TextView) view.findViewById(com.apps.nies.apps.R.id.tv_item_time1);
            button = (TextView) view.findViewById(R.id.tv_item_button);
            viewtime1 = (TextView)view.findViewById(R.id.tv_item_time1);
            viewtime2= (TextView)view.findViewById(R.id.tv_item_time2);
            ll = (LinearLayout)view.findViewById(R.id.xxxx);
            new Thread(runnable).start();
        }
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                demoHandler.sendEmptyMessage(111);
            }
        };
        Handler demoHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                long cttime = ccc;
                long ctime = System.currentTimeMillis();
                long aa = 7200000 - (ctime-cttime);
                String time1 = Data.getTimeHH(aa)+":"+Data.getTimeMM(aa);
                String time2 = ":"+Data.getTimeSS(aa);
                viewtime1.setText(time1);
                viewtime2.setText(time2);
                postDelayed(runnable, 200);
            }
        };
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                MainActivity.mmmm).inflate(R.layout.xx_layout, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        holder.tv.setText(Data.vUser.get(position).Name);
        holder.ccc = Data.vUser.get(position).CreateTime;
//        holder.tv2.setText("剩余时间"+MainActivity.Time);
//        holder.button.setText("详情");
        long cttime = holder.ccc;
        long ctime = System.currentTimeMillis();
        long aa = Data.vUser.get(position).MaxTime - (ctime-cttime);
        String time1 = Data.getTimeHH(aa)+":"+Data.getTimeMM(aa);
        String time2 = ":"+Data.getTimeSS(aa);
        holder.viewtime1.setText(time1);
        holder.viewtime2.setText(time2);

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注意，这里的position不要用上面参数中的position，会出现位置错乱\
//               onItemClickListener.OnItemClickListener(holder.button, holder.getLayoutPosition());
//                Log.v("yououaaa","TTTTTTTdfdfd  "+  holder.getLayoutPosition());
                  Data.GetUserId =  holder.getLayoutPosition();
                  MainActivity.toGetAct();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return Data.vUser.size();
    }
    private OnItemClickListener onItemClickListener;
    interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }
}