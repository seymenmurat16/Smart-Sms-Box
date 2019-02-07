package com.example.murat.smartsmsbox;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    View v;
    private RecyclerView myrecyclerview;
    private List<Sms2> lstMessage;
    private List<Contact> lstContact;
    MyRoomDatabase database;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lstMessage = new ArrayList<>();
        SmsApplication application = (SmsApplication) getApplication();
        database = application.getDatabase();
        MessageDao mdao = database.messageDao();
        PersonDao dao = database.personDao();
        lstContact = new ArrayList<>();
        List<Person> everyone = dao.getAllPeople();
        List<Message> messages = mdao.getAllMessages();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            for (int i = 0; i< messages.size();i++){
                address = extras.getString("MessageNumber");
                if(messages.get(i).getAddress().equals(address)){
                    for (int j = 0; j< everyone.size();j++){
                        if(everyone.get(j).getPhone().equals(address)){
                            address = everyone.get(j).getName();
                        }
                    }
                    lstMessage.add(new Sms2(address, messages.get(i).getBody(), messages.get(i).getType(),messages.get(i).getDate()));
                }
            }
        }

        RecyclerView myrecyclerview = findViewById(R.id.list_recyclerview);
        RecyclerSmsViewAdapter recyclerAdapter = new RecyclerSmsViewAdapter(this,lstMessage);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myrecyclerview.setAdapter(recyclerAdapter);
    }
}
