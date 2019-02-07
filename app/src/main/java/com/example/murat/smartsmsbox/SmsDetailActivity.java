package com.example.murat.smartsmsbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SmsDetailActivity extends AppCompatActivity {

    MyRoomDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms__detail);
        SmsApplication application = (SmsApplication) getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String address = extras.getString("MessageNumber");
            String message = extras.getString("Message");
            Double lon = extras.getDouble("lon");
            Double lat = extras.getDouble("lat");
            TextView addressField = (TextView) findViewById(R.id.from2);
            TextView messageField = (TextView) findViewById(R.id.mesaj2);

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
