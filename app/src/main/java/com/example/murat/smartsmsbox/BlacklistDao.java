package com.example.murat.smartsmsbox;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Murat on 30.05.2018.
 */

@Dao
public interface BlacklistDao {

    @Insert
    void insertPhone(Blacklist phone);

    @Insert
    void insertPhones(List<Blacklist> phones);

    @Delete
    void deletePhone(Blacklist phone);

    @Update
    void updatePhone(Blacklist phone);

    @Query("SELECT * FROM blacklist")
    List<Blacklist> getAllPhones();

    @Query("DELETE FROM blacklist")
    void deleteall();

    @Query("DELETE FROM blacklist where bl_phone = :number")
    void deleteOne(String number);


    @Query("SELECT * FROM blacklist WHERE bid = :bId")
    Blacklist getPhone(int bId);
}
