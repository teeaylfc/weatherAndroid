package com.example.myapplication1.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication1.R;

public class FlashActivity extends AppCompatActivity {

    private ViewPager pager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_screen);
        addControl();
    }

    private void addControl() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapterz adapter = new PagerAdapterz(manager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(1);

    }
}
