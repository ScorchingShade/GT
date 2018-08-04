package com.ankushinc.reddragon.gymtracker;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class Tab1_TabbedSpace extends Fragment {
    private final String TAG="Tab1Fragment";
    Context ctx;
  private  AdView mAdview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.tab1_fragment,container,false);


        MobileAds.initialize(getActivity(),"ca-app-pub-3847635009923151~7567979933");

        mAdview=(AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        return view;
    }
}
