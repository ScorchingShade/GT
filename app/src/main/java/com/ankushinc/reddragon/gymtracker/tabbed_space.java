package com.ankushinc.reddragon.gymtracker;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class tabbed_space extends AppCompatActivity {
    public ViewPager viewPager;
    private static final String TAG = "tabbed_space";
    private SectionsPageAdapter mSectionsPageAdapter;
    private Toolbar toolbar;

   AdView mAdview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_space);
        Log.d(TAG, "onCreate: Starting");

        MobileAds.initialize(this,"ca-app-pub-3847635009923151~7567979933");

        mAdview=(AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);



        SharedPreferences prefs =getSharedPreferences("prefs",MODE_PRIVATE);
       boolean firstStart=prefs.getBoolean("firstStart",true);

       if(firstStart){
           initPage();
       }




        //setting the back option on toolbar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



      //create the functions for the tabbed activity!
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

   private void initPage(){



       SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
       SharedPreferences.Editor editor = prefs.edit();
       editor.putBoolean("firstStart",false);
       editor.apply();

   }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1_TabbedSpace(), "Homej bm");
        adapter.addFragment(new Tab2_TabbedSpace(), "Home");
        adapter.addFragment(new Tab3_TabbedSpace(), "Home");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return super.onOptionsItemSelected(item);
    }

}
