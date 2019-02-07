package com.example.murat.smartsmsbox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Murat on 30.05.2018.
 */


@Entity(tableName = "blacklist")
public class Blacklist {
    public Blacklist(int bid, String phone) {
        this.bid = bid;
        this.phone = phone;

    }
    @PrimaryKey(autoGenerate = true)
    private int bid;

    @ColumnInfo(name = "bl_phone")
    private String phone;

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
