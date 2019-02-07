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

public class FragmentOther extends android.support.v4.app.Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<Sms> lstOther;
    private List<Contact> lstContact;
    MyRoomDatabase database;

    public FragmentOther() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstOther = new ArrayList<>();
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
            if(messages2.get(i).getCategory()==0) {
                for(int z=0; z < lstOther.size();z++){
                    if(messages2.get(i).getAddress().equals(lstOther.get(z).getAddress())){
                        ekle = 1;
                    }
                }
                if(ekle == 0) {
                    address = messages2.get(i).getAddress();
                    lstOther.add(new Sms(address, messages2.get(i).getBody(), messages2.get(i).getType()));
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.category_other_fragment,container,false);
        myrecyclerview = v.findViewById(R.id.other_recyclerview);
        RecyclerSmsViewAdapter2 recyclerAdapter = new RecyclerSmsViewAdapter2(getContext(),lstOther);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);
        return v;
    }
}
