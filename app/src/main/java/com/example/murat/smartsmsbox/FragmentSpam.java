package com.example.murat.smartsmsbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Murat on 3.06.2018.
 */

public class FragmentSpam extends android.support.v4.app.Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<Sms> lstSpam;
    private List<Contact> lstContact;
    MyRoomDatabase database;

    public FragmentSpam() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstSpam = new ArrayList<>();
        SmsApplication application = (SmsApplication) getActivity().getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        MessageDao mdao = database.messageDao();
        lstContact = new ArrayList<>();
        List<Message> messages2 = mdao.getAllMessages();
        String address;
        int ekle;
        for (int i = messages2.size() - 1; i > -1; i--) {
            ekle = 0;
            if(messages2.get(i).getCategory()==4) {
                for(int z=0; z < lstSpam.size();z++){
                    if(messages2.get(i).getAddress().equals(lstSpam.get(z).getAddress())){
                        ekle = 1;
                    }
                }
                if(ekle == 0) {
                    address = messages2.get(i).getAddress();
                    lstSpam.add(new Sms(address, messages2.get(i).getBody(), messages2.get(i).getType()));
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.category_spam_fragment,container,false);
        myrecyclerview = v.findViewById(R.id.spam_recyclerview);
        RecyclerSmsViewAdapter2 recyclerAdapter = new RecyclerSmsViewAdapter2(getContext(),lstSpam);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);
        return v;
    }
}
