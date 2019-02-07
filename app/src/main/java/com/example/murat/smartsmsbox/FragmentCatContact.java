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

public class FragmentCatContact extends android.support.v4.app.Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<Sms> lstCatContact;
    private List<Sms> lstCats;
    private List<Contact> lstContact;
    MyRoomDatabase database;
    String address;

    public FragmentCatContact() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstCatContact = new ArrayList<>();
        lstCats = new ArrayList<>();
        SmsApplication application = (SmsApplication) getActivity().getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        MessageDao mdao = database.messageDao();
        int ekle;
        lstContact = new ArrayList<>();
        List<Message> messages2 = mdao.getAllMessages();
        List<Person> everyone2 = dao.getAllPeople();
        for (int i = messages2.size() - 1; i > -1; i--) {
            ekle = 0;
            if(messages2.get(i).getCategory()==1) {
                for(int z=0; z < lstCats.size();z++){
                    Log.i("LSTCATCONTACT:", " address " + messages2.get(i).getAddress());
                    Log.i("LSTCATS:", " address " + lstCats.get(z).getAddress());
                    if(messages2.get(i).getAddress().equals(lstCats.get(z).getAddress())){
                        ekle = 1;
                        Log.i("EKLEDİ:", " address " + lstCats.get(z).getAddress());
                    }
                }
                if(ekle == 0) {
                    address = messages2.get(i).getAddress();
                    Log.i("MESAJ:", " ilk  " + address);
                    lstCats.add(new Sms(address, messages2.get(i).getBody(), messages2.get(i).getType()));
                    for (int j = 0; j < everyone2.size(); j++) {
                        if (everyone2.get(j).getPhone().equals(messages2.get(i).getAddress()) && !everyone2.get(j).getPhone().equals(null)) {
                            address = (everyone2.get(j).getName());
                        }
                    }
                    Log.i("MESAJ:", " address " + address);
                }
                lstCatContact.add(new Sms(address, messages2.get(i).getBody(), messages2.get(i).getType()));
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.category_contact_fragment,container,false);
        myrecyclerview = v.findViewById(R.id.categorycontact_recyclerview);
        RecyclerSmsViewAdapter2 recyclerAdapter = new RecyclerSmsViewAdapter2(getContext(),lstCats);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);
        return v;
    }
}
