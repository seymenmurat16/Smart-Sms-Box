package com.example.murat.smartsmsbox;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Murat on 30.05.2018.
 */

@Database(entities = {Person.class,Message.class,Blacklist.class}, version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
    public abstract MessageDao messageDao();
    public abstract BlacklistDao blacklistDao();
}