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
public interface MessageDao {

    @Insert
    void insertMessage(Message message);

    @Insert
    void insertMessages(List<Message> messsages);

    @Delete
    void deleteMessage(Message message);

    @Update
    void updateMessage(Message message);

    @Query("SELECT * FROM message")
    List<Message> getAllMessages();

    @Query("SELECT * FROM message WHERE message_body like '%B__0%' or message_body like '%B__1%' or message_body like '%B__2%' or" +
            " message_body like '%B__3%' or message_body like '%B__4%' or message_body like '%B__5%' or message_body like '%B__6%' or" +
            " message_body like '%B__7%' or message_body like '%B__8%' or message_body like '%B__9%' ")
    List<Message> getCategoryCommerical();

    @Query("SELECT * FROM message WHERE message_body like '%kodu%' or message_body like '%Kodu%' or message_body like '%code%' or message_body like '%Code'")
    List<Message> getCategoryOTP();

    @Query("DELETE FROM message")
    void deleteallmessage();

    @Query("DELETE FROM message where message_body = :number")
    void deleteOne(String number);

    @Query("UPDATE message SET message_category=1 WHERE message_body like '%B__0%' or message_body like '%B__1%' or message_body like '%B__2%' or" +
            " message_body like '%B__3%' or message_body like '%B__4%' or message_body like '%B__5%' or message_body like '%B__6%' or" +
            " message_body like '%B__7%' or message_body like '%B__8%' or message_body like '%B__9%'")
    void updateCommerical();

    @Query("UPDATE message SET message_category=1 WHERE message_body like '%B__0%' or message_body like '%B__1%' or message_body like '%B__2%' or" +
            " message_body like '%B__3%' or message_body like '%B__4%' or message_body like '%B__5%' or message_body like '%B__6%' or" +
            " message_body like '%B__7%' or message_body like '%B__8%' or message_body like '%B__9%'")
    void updateOTP();


    @Query("SELECT * FROM message WHERE mid = :messageId")
    Message getMessage(int messageId);
}
