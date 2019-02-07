package com.example.murat.smartsmsbox;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Murat on 30.05.2018.
 */

public class FragmentContact extends android.support.v4.app.Fragment {
    View v;
    MyRoomDatabase database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SmsApplication application = (SmsApplication) getActivity().getApplication();
        database = application.getDatabase();
        PersonDao dao = database.personDao();
        lstContact = new ArrayList<>();

        List<Person> everyone = dao.getAllPeople();
        if (ContextCompat.checkSelfPermission(getActivity().getBaseContext(), "android.permission.READ_CONTACTS") == PackageManager.PERMISSION_GRANTED) {
            if (everyone.isEmpty()) {

                ContentResolver resolver = getActivity().getContentResolver();
                Cursor cur = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

                if ((cur != null ? cur.getCount() : 0) > 0) {
                    while (cur != null && cur.moveToNext()) {
                        String id = cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME));

                        if (cur.getInt(cur.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                            Cursor pCur = resolver.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                String phoneNo = pCur.getString(pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));

                                dao.insertPerson(new Person(0, name, phoneNo));
                            }

                            pCur.close();
                        }

                    }
                }
                if (cur != null) {
                    cur.close();
                }

            }
        }else{
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_CONTACTS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        int var = 0;
        List<Person> everyone2 = dao.getAllPeople();
        for(int i=0;i<everyone2.size();i++){
            for (int j = 0; j < lstContact.size(); j++) {
                if (lstContact.get(j).getName().equals(everyone2.get(i).getName())) {
                    var = 1;
                }
            }
            if (var == 0) {
                lstContact.add(new Contact(everyone2.get(i).getName(),everyone.get(i).getPhone(),R.drawable.facebook_avatar));
            }
            var =0 ;
        }
        Log.i("Sayısı", "sayısı" + everyone.size());

    }



    private RecyclerView myrecyclerview;
    private List<Contact> lstContact;

    public FragmentContact() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.contact_fragment,container,false);
        myrecyclerview = v.findViewById(R.id.contact_recylerview);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(),lstContact);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerAdapter);
        return v;
    }

}
