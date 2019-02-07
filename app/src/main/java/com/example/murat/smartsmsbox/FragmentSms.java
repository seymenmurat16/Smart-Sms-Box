package com.example.murat.smartsmsbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Murat on 30.05.2018.
 */

public class FragmentSms extends android.support.v4.app.Fragment{
    View v;
    String mid,address,body,type,date;
    int typei;
    private RecyclerView myrecyclerview;
    private List<Sms> lstSms;
    private List<Contact> lstContact;
    MyRoomDatabase database;

    public FragmentSms() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstSms = new ArrayList<>();
        SmsApplication application = (SmsApplication) getActivity().getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        MessageDao mdao = database.messageDao();
        BlacklistDao bdao = database.blacklistDao();
        lstContact = new ArrayList<>();
        List<Message> messages = mdao.getAllMessages();
        if (ContextCompat.checkSelfPermission(getActivity().getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            if (messages.isEmpty()) {
                Cursor cursor = getActivity().getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
                cursor.moveToLast();
                if (cursor.moveToLast()) { // must check the result to prevent exception
                    do {
                        String msgData = "";
                        for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                            mid = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                            address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                            body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                            type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                            typei = Integer.parseInt(type.toString());
                            date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                        }
                        //Log.i("Mesaj ", " mid :" + mid + " address :" + address + " body :" + body + " type :" + type + " date :" + date);

                        mdao.insertMessage(new Message(0, mid, address, body, typei, date, 0, 0, 0));
                /*List<Person> everyone2 = dao.getAllPeople();
                for(int i=0;i<everyone2.size();i++){
                    if(everyone2.get(i).getPhone().equals(address)){
                        address = (everyone2.get(i).getName());
                    }
                }
                lstSms.add(new Sms(address,body,typei));*/
                    } while (cursor.moveToPrevious());
                } else {
                    // empty box, no SMS
                }
            }
        }else{
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        List<Message> messages3 = mdao.getAllMessages();
        List<Person> everyone3 = dao.getAllPeople();
        List<Message> commerical = mdao.getCategoryCommerical();
        List<Message> otp = mdao.getCategoryOTP();
        for (int i = commerical.size() - 1; i > -1; i--) {
            commerical.get(i).setCategory(2);
            mdao.updateMessage(commerical.get(i));
        }
        for (int i = otp.size() - 1; i > -1; i--) {
            otp.get(i).setCategory(3);
            mdao.updateMessage(otp.get(i));
        }
        List<Message> messages2 = mdao.getAllMessages();
        List<Blacklist> black = bdao.getAllPhones();
        for (int i = messages2.size() - 1; i > -1; i--) {
            for (int j = 0; j < black.size(); j++) {
                if (black.get(j).getPhone().equals(messages2.get(i).getAddress())) {
                    messages2.get(i).setCategory(4);
                    mdao.updateMessage(messages2.get(i));
                }
            }
        }
        for (int i = messages3.size() - 1; i > -1; i--) {
            for (int j = 0; j < everyone3.size(); j++) {
                if (everyone3.get(j).getPhone().equals(messages3.get(i).getAddress())) {
                    messages3.get(i).setCategory(1);
                    mdao.updateMessage(messages3.get(i));
                }
            }
        }
        String address;
        int ekle;
        messages2=mdao.getAllMessages();
        List<Person> everyone2 = dao.getAllPeople();
        for (int i = messages2.size() - 1; i > -1; i--) {
            ekle = 0;
                for(int z=0; z < lstSms.size();z++){
                    if(messages2.get(i).getAddress().equals(lstSms.get(z).getAddress())){
                        ekle = 1;
                    }
                }
                if(ekle == 0) {
                    address = messages2.get(i).getAddress();
                    lstSms.add(new Sms(address, messages2.get(i).getBody(), messages2.get(i).getType()));
                    for (int j = 0; j < everyone2.size(); j++) {
                        if (everyone2.get(j).getPhone().equals(messages2.get(i).getAddress()) && !everyone2.get(j).getPhone().equals(null)) {
                            address = (everyone2.get(j).getName());
                        }
                    }
                }
        }
        Log.i("TOPLAM:", " " + messages2.size());
    }


    /*@Override
    public void onResume() {
        super.onResume();
        SmsApplication application = (SmsApplication) getActivity().getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        MessageDao mdao = database.messageDao();
        List<Message> messages2 = mdao.getAllMessages();
        for(int i=messages2.size()-1;i>-1;i--){
            address = messages2.get(i).getAddress();
            List<Person> everyone2 = dao.getAllPeople();
            for(int j=0;j<everyone2.size();j++){
                if(everyone2.get(j).getPhone().equals(messages2.get(i).getAddress())){
                    address = (everyone2.get(j).getName());
                }
            }
            lstSms.add(new Sms(address,messages2.get(i).getBody(),messages2.get(i).getType()));
        }

    }*/


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sms_fragment,container,false);
        myrecyclerview = v.findViewById(R.id.sms_recyclerview);
        RecyclerSmsViewAdapter2 recyclerAdapter = new RecyclerSmsViewAdapter2(getContext(),lstSms);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);
        return v;
    }



}
