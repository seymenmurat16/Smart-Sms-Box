package com.example.murat.smartsmsbox;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CategoriesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        tabLayout = (TabLayout) findViewById(R.id.tabloyut_id2);
        viewPager = findViewById(R.id.viewpager_id2);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // ADD FRAGMENT HERE
        adapter.AddFragment(new FragmentCatContact(),"Asd");
        adapter.AddFragment(new FragmentCommerical(),"Ba");
        adapter.AddFragment(new FragmentOtp(),"As");
        adapter.AddFragment(new FragmentSpam(),"Ca");
        adapter.AddFragment(new FragmentOther(),"Csd");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("CONT");
        tabLayout.getTabAt(1).setText("COM");
        tabLayout.getTabAt(2).setText("OTP");
        tabLayout.getTabAt(3).setText("SPAM");
        tabLayout.getTabAt(4).setText("OTHERS");


        //Remove Shadow From Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }
}
