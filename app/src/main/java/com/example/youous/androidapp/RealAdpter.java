package com.example.youous.androidapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by youous on 2016/8/22.
 */
public class RealAdpter extends RecyclerView.Adapter<RealAdpter.MyViewHolder>
{

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        @InjectView(R.id.real_list)

        RelativeLayout ll;
        ImageView image;
        TextView tv1,tv2;

        public MyViewHolder(View view)
        {
            super(view);
            ButterKnife.inject(itemView);
            ll = (RelativeLayout)view.findViewById(R.id.realxxx);
            image = (ImageView)view.findViewById(R.id.realxx_image);
            tv1 = (TextView) view.findViewById(R.id.realxx_tv1);
            tv2 = (TextView) view.findViewById(R.id.realxx_tv2);


        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                RealListActivity.mmmm).inflate(R.layout.realxx_layout, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
//        holder.tv1.setText("视频"+(position+1));
        holder.tv1.setText("金狮店");
        holder.tv2.setText(EzvizApplication.result.get(position).getCameraName());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        RealPlayActivity.Id = position;
        RealListActivity.mmmm.startActivity(new Intent(RealListActivity.mmmm,RealPlayActivity.class));
        RealListActivity.mmmm.finish();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return EzvizApplication.result.size();
    }
    private OnItemClickListener onItemClickListener;
    interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }
}
