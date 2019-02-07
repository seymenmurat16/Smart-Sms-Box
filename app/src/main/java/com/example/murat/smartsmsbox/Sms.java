package com.example.murat.smartsmsbox;

/**
 * Created by Murat on 30.05.2018.
 */

public class Sms {
    private String address;
    private  String body;
    private int type;

    public Sms() {

    }

    public Sms(String address, String body,int type) {
        this.address = address;
        this.body = body;
        this.type = type;
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
}


