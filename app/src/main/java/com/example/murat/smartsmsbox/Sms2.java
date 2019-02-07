package com.example.murat.smartsmsbox;

/**
 * Created by Murat on 30.05.2018.
 */

public class Sms2 {
    private String address;
    private  String body;
    private String time;
    private int type;

    public Sms2() {

    }

    public Sms2(String address, String body, int type,String time) {
        this.address = address;
        this.body = body;
        this.type = type;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


