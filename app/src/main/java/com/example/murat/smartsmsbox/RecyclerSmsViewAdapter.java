package com.example.murat.smartsmsbox;

/**
 * Created by Murat on 30.05.2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerSmsViewAdapter extends  RecyclerView.Adapter{

    Context mContext;
    List<Sms2> mData;
    MyRoomDatabase database;
    private static final int VIEW_TYPE_MESSAGE_SENT = 2;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 1;

    public RecyclerSmsViewAdapter(Context mContext, List<Sms2> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public int getItemViewType(int position) {
         Sms2 message = mData.get(position);

        if (message.getType() == 2) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            final MyViewHolder vHolder = new MyViewHolder(view);
            vHolder.items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), SmsDetailActivity.class);
                    i.putExtra("MessageNumber",mData.get(vHolder.getAdapterPosition()).getAddress());
                    i.putExtra("Message",mData.get(vHolder.getAdapterPosition()).getBody());
                    mContext.startActivity(i);
                }
            });
            return vHolder;
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            final MyViewHolder2 vHolder2 = new MyViewHolder2(view);
            vHolder2.items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), SmsDetailActivity.class);
                    i.putExtra("MessageNumber",mData.get(vHolder2.getAdapterPosition()).getAddress());
                    i.putExtra("Message",mData.get(vHolder2.getAdapterPosition()).getBody());
                    mContext.startActivity(i);
                }
            });
            return vHolder2;
        }

       return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((MyViewHolder) holder).sms_body.setText(mData.get(position).getBody());
                String time2  = mData.get(position).getTime();
                long l2 = Long.parseLong(time2);
                String dateString2 = toYYYYMMDDHHMMSS2(l2);
                ((MyViewHolder) holder).sms_time.setText(dateString2);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((MyViewHolder2) holder).sms_address.setText(mData.get(position).getAddress());
                ((MyViewHolder2) holder).sms_body.setText(mData.get(position).getBody());
                String time  = mData.get(position).getTime();
                long l = Long.parseLong(time);
                String dateString = toYYYYMMDDHHMMSS(l);
                ((MyViewHolder2) holder).sms_time.setText(dateString);
        }


    }

    public static String toYYYYMMDDHHMMSS(long time) {

        Date date=new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy\nHH:mm");
        String dateText = df2.format(date);
        return dateText;

    }

    public static String toYYYYMMDDHHMMSS2(long time) {

        Date date=new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy\n       HH:mm");
        String dateText = df2.format(date);
        return dateText;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout items;

        private TextView sms_body;
        private TextView sms_time;


        public MyViewHolder(View itemView) {
            super(itemView);
            items = itemView.findViewById(R.id.sent_recycler);
            sms_body = itemView.findViewById(R.id.text_message_body2);
            sms_time = items.findViewById(R.id.text_message_time2);
        }
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        private ConstraintLayout items;
        private TextView sms_address;
        private TextView sms_body;
        private TextView sms_time;


        public MyViewHolder2(View itemView) {
            super(itemView);
            items = itemView.findViewById(R.id.received_items);
            sms_address = itemView.findViewById(R.id.text_message_name);
            sms_body = itemView.findViewById(R.id.text_message_body);
            sms_time = items.findViewById(R.id.text_message_time);
        }
    }

}
