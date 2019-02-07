package com.example.murat.smartsmsbox;

/**
 * Created by Murat on 30.05.2018.
 */

import android.app.Activity;
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

public class RecyclerSmsViewAdapter2 extends  RecyclerView.Adapter{

    Context mContext;
    List<Sms> mData;
    MyRoomDatabase database;
    String address;

    public RecyclerSmsViewAdapter2(Context mContext, List<Sms> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_sms,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        vHolder.items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ListActivity.class);
                i.putExtra("MessageNumber",mData.get(vHolder.getAdapterPosition()).getAddress());
                i.putExtra("Message",mData.get(vHolder.getAdapterPosition()).getBody());
                mContext.startActivity(i);
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SmsApplication application = (SmsApplication) ((Activity) mContext).getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        List<Person> everyone = dao.getAllPeople();
        Log.i("TAGGGGGGGGGGG" , "  " + everyone);
        address = mData.get(position).getAddress();
        for (int j = 0; j< everyone.size();j++){
            if(everyone.get(j).getPhone().equals(address)){
                address = everyone.get(j).getName();
            }
        }
        ((MyViewHolder) holder).sms_address.setText(address);
        ((MyViewHolder) holder).sms_body.setText(mData.get(position).getBody());
        if (mData.get(position).getType() == 2){
            ((MyViewHolder) holder).image.setColorFilter(mContext.getResources().getColor(R.color.colorAccent));
        }
        else{
            ((MyViewHolder) holder).image.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout items;
        private TextView sms_address;
        private TextView sms_body;
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            items = itemView.findViewById(R.id.sms_items);
            sms_address = itemView.findViewById(R.id.sms_address);
            sms_body = itemView.findViewById(R.id.sms_body);
            image = itemView.findViewById(R.id.sms_image);
        }
    }
}
