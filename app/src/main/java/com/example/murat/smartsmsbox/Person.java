package com.example.murat.smartsmsbox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;

/**
 * Created by Murat on 30.05.2018.
 */


@Entity(tableName = "person")
public class Person {
    public Person(int uid, String name, String phone) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;

    }
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "person_name")
    private String name;

    @ColumnInfo(name = "person_phone")
    private String phone;


    public int getUid() {
        return uid;
    }

    public void setUid(int  uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
