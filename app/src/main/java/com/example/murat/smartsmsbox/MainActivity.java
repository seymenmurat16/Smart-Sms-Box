package com.example.murat.smartsmsbox;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabloyut_id);
        viewPager = findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // ADD FRAGMENT HERE
        adapter.AddFragment(new FragmentCall(),"");
        adapter.AddFragment(new FragmentSms(),"");
        adapter.AddFragment(new FragmentContact(),"");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_credit_card);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_message);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_call);

        //Remove Shadow From Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);





    }


}
