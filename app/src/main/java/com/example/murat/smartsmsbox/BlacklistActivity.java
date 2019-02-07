package com.example.murat.smartsmsbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class BlacklistActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener{

    View v;
    List<BlacklistPhone> lstBlacklist;
    private RecyclerView myrecyclerview;;
    Button add;
    MyRoomDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        lstBlacklist = new ArrayList<>();
        add = findViewById(R.id.addphone);
        SmsApplication application = (SmsApplication) getApplication();
        database = application.getDatabase();
        BlacklistDao bdao = database.blacklistDao();
        MessageDao mdao = database.messageDao();
        List<Blacklist> everyone = bdao.getAllPhones();
        for(int i=0;i<everyone.size();i++){
            Log.i("BLACKLİST"," " + i + ".eleman: " + everyone.get(i).getPhone());
            lstBlacklist.add(new BlacklistPhone(everyone.get(i).getPhone()));
        }
        RecyclerView myrecyclerview = findViewById(R.id.blacklist_recylerview);
        RecyclerBlacklistViewAdapter recyclerAdapter = new RecyclerBlacklistViewAdapter(this,lstBlacklist);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myrecyclerview.setAdapter(recyclerAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((String ) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(myrecyclerview);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }


    public void removeItem(final String number){
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this phone?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SmsApplication application = (SmsApplication) getApplication();
                        database = application.getDatabase();
                        BlacklistDao bdao = database.blacklistDao();
                        MessageDao mdao = database.messageDao();
                        List<Message> messages = mdao.getAllMessages();
                        bdao.deleteOne(number);
                        for(int i = 0; i<messages.size(); i++ ){
                            if(messages.get(i).getAddress().equals(number)){
                                messages.get(i).setCategory(0);
                                mdao.updateMessage(messages.get(i));
                            }
                        }
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
    }

    @Override
    public void applyTexts(String phone) {
        SmsApplication application = (SmsApplication) getApplication();
        database = application.getDatabase();
        BlacklistDao bdao = database.blacklistDao();
        MessageDao mdao = database.messageDao();
        List<Message> messages = mdao.getAllMessages();
        for(int i = 0; i<messages.size(); i++ ){
            if(messages.get(i).getAddress().equals(phone)){
                messages.get(i).setCategory(4);
                mdao.updateMessage(messages.get(i));
            }
        }
        bdao.insertPhone(new Blacklist(0,phone));
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}
