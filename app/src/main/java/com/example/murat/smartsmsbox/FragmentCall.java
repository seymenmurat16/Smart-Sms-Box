package com.example.murat.smartsmsbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Murat on 30.05.2018.
 */

public class FragmentCall extends android.support.v4.app.Fragment {

    View v;
    Button btn1 ,send,blacklist,category;
    MyRoomDatabase database;
    public FragmentCall() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SmsApplication application = (SmsApplication) getActivity().getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        MessageDao mdao = database.messageDao();
        BlacklistDao bdao = database.blacklistDao();
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.call_fragment,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn1 = getView().findViewById(R.id.locate);
        send = getView().findViewById(R.id.sendsmsbutton);
        blacklist = getView().findViewById(R.id.blacklistbutton);
        category = getView().findViewById(R.id.categorybutton);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SendSms.class);
                startActivity(intent);
            }
        });
        blacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BlacklistActivity.class);
                startActivity(intent);
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoriesActivity.class);
                startActivity(intent);
            }
        });
    }

}
