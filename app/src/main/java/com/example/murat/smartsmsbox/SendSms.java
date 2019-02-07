package com.example.murat.smartsmsbox;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

    public class SendSms extends AppCompatActivity {

    EditText txtPhoneNo;
    EditText txtMessage;
    Button btnSend;
    String phoneNo, message;
    String mid;
    LocationManager lm;
    LocationListener listener;
    double longitude=0 ,latitude=0;
    private static final int PERMISSION_REQUEST_CODE = 1;
    MyRoomDatabase database;




        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        txtPhoneNo = (EditText) this.findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) this.findViewById(R.id.txtMessage);
        btnSend = (Button) this.findViewById(R.id.btnSend);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String address = extras.getString("MessageNumber");
            txtPhoneNo.setText(address, TextView.BufferType.EDITABLE);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String address = extras.getString("MessageNumber");
                    phoneNo = address;
                    message = txtMessage.getText().toString();
                    Log.i("Tag","1");
                }else{
                    phoneNo = txtPhoneNo.getText().toString();
                    message = txtMessage.getText().toString();
                    Log.i("Tag","22");
                }
                sendMessage(phoneNo, message);
            }
        });
    }

    // Function send message sms
    private void sendMessage(String phoneNo, String message){
        SmsApplication application = (SmsApplication) getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        MessageDao mdao = database.messageDao();
        long time= System.currentTimeMillis();
        String s = String.valueOf(time);
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        cursor.moveToFirst();
        mid = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        int mesid = Integer.parseInt(mid);
        mesid = mesid + 1;
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( lm.isProviderEnabled( LocationManager.GPS_PROVIDER )){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            }else {
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
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                if (checkSelfPermission(Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_DENIED) {

                    Log.d("permission", "permission denied to SEND_SMS - requesting it");
                    String[] permissions = {Manifest.permission.SEND_SMS};

                    requestPermissions(permissions, PERMISSION_REQUEST_CODE);

                }
            }
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            mdao.insertMessage(new Message(0,String.valueOf(mesid), phoneNo, message, 2,s, 10, 0, 0));
            Toast.makeText(getApplicationContext(), "SMS Sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS Fail. Please try again!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}
