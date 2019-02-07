package com.example.murat.smartsmsbox;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class SMS_Receive extends AppCompatActivity {
    String mid,address,body,type,date;
    int typei;
    MyRoomDatabase database;

    /*  Button msg;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms__receive);
        SmsApplication application = (SmsApplication) getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        MessageDao mdao = database.messageDao();
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        cursor.moveToFirst();
        mid = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
        body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
        type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
        typei = Integer.parseInt(type.toString());
        date = cursor.getString(cursor.getColumnIndexOrThrow("date"));


        // Todo : Receive Message And Number In Intent

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String address = extras.getString("MessageNumber");
            String message = extras.getString("Message");
            Double lon = extras.getDouble("lon");
            Double lat = extras.getDouble("lat");
            Log.i("Mesaj ", " mid :" + mid + " address :" + address + " body :" + body + " type :" + type + " date :" + date + "lon :" + lon + "lat :" + lat);
            mdao.insertMessage(new Message(0, mid, address, message, typei, date, 0, lon, lat));
            TextView addressField = (TextView) findViewById(R.id.from);
            TextView messageField = (TextView) findViewById(R.id.mesaj);

            // Todo : Set Number And Message In TextView
            List<Person> everyone2 = dao.getAllPeople();
            for(int j=0;j<everyone2.size();j++){
                if(everyone2.get(j).getPhone().equals(address)){
                    address = (everyone2.get(j).getName());
                }
            }

            addressField.setText(address);
            messageField.setText(message);
        }
        Button msg = (Button)findViewById(R.id.btn_msg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
