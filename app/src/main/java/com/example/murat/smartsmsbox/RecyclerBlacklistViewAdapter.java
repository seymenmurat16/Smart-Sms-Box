package com.example.murat.smartsmsbox;

/**
 * Created by Murat on 30.05.2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerBlacklistViewAdapter extends  RecyclerView.Adapter<RecyclerBlacklistViewAdapter.MyViewHolder>{

    Context mContext;
    List<BlacklistPhone> mData;

    public RecyclerBlacklistViewAdapter(Context mContext, List<BlacklistPhone> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_blacklist,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i("BLACK"," " + holder.black);
        holder.black.setText(mData.get(position).getPhone());
        String number = mData.get(position).getPhone();
        holder.itemView.setTag(number);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView black;

        public MyViewHolder(View itemView) {
            super(itemView);
            black = itemView.findViewById(R.id.blacklist_address);
        }
    }

}
