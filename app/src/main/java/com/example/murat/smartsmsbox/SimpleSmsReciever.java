package com.example.murat.smartsmsbox;

/**
 * Created by ismail on 7/12/17.
 */

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;

// Todo : SMS Reciever Class

public class SimpleSmsReciever extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    private String messageBody = "", messageAddress = "";
    String mid,address,body,type,date;
    int typei;
    LocationManager lm;
    LocationListener listener;
    double longitude,latitude;
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {

                messageBody = "";
                messageAddress = "";

                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];

                for (int i = 0; i < pdus.length; i++) {
                    messageBody += SmsMessage.createFromPdu((byte[]) pdus[i]).getDisplayMessageBody();
                }
                messageAddress = SmsMessage.createFromPdu((byte[]) pdus[0]).getDisplayOriginatingAddress();

                lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if ( lm.isProviderEnabled( LocationManager.GPS_PROVIDER )){
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        Log.i("Location", "lat: " + latitude + " long: " + longitude);
                    } else {
                        listener = new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();
                                Log.i("Location", "lat: " + latitude + " long: " + longitude);
                            }

                            @Override
                            public void onStatusChanged(String s, int i, Bundle bundle) {

                            }

                            @Override
                            public void onProviderEnabled(String s) {

                            }

                            @Override
                            public void onProviderDisabled(String s) {

                            }
                        };
                        lm.requestLocationUpdates("gps", 5000, 0, listener);
                    }
                }
                Intent smsIntent = new Intent(context, SMS_Receive.class);
                smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                smsIntent.putExtra("MessageNumber", messageAddress);
                smsIntent.putExtra("Message", messageBody);
                smsIntent.putExtra("lon", longitude);
                smsIntent.putExtra("lat",latitude);
                context.startActivity(smsIntent);
            }
        }
    }

}
