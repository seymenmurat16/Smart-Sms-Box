package com.example.murat.smartsmsbox;

import android.app.Application;
import android.arch.persistence.room.Room;

/**
 * Created by Murat on 30.05.2018.
 */

public class SmsApplication extends Application {
    private MyRoomDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(), MyRoomDatabase.class, "sms-box")
                .allowMainThreadQueries() //MAIN THREAD'DE İŞLEM YAPMAYINIZ
                .build();
    }

    public MyRoomDatabase getDatabase(){
        return database;
    }
}
