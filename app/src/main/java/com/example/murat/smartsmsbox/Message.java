package com.example.murat.smartsmsbox;

/**
 * Created by Murat on 31.05.2018.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;

/**
 * Created by Murat on 30.05.2018.
 */


@Entity(tableName = "message")
public class Message {
    public Message(int mid, String mesid, String address, String body, int type , String date,int category,double lon,double lat) {
        this.mid = mid;
        this.mesid = mesid;
        this.address = address;
        this.body = body;
        this.type = type;
        this.date = date;
        this.category = category;
        this.lon = lon;
        this.lat = lat;
    }

    @PrimaryKey(autoGenerate = true)
    private int mid;

    @ColumnInfo(name = "message_mesid")
    private String mesid;

    @ColumnInfo(name = "message_address")
    private String address;

    @ColumnInfo(name = "message_body")
    private String body;

    @ColumnInfo(name = "message_type")
    private int type;

    @ColumnInfo(name = "message_date")
    private String date;

    @ColumnInfo(name = "message_category")
    private int category;

    @ColumnInfo(name = "message_lon")
    private double lon;

    @ColumnInfo(name = "message_lat")
    private double lat;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMesid() {
        return mesid;
    }

    public void setMesid(String mesid) {
        this.mesid = mesid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
