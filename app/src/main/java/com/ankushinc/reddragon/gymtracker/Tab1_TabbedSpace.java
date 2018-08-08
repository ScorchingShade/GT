package com.ankushinc.reddragon.gymtracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class Tab1_TabbedSpace extends Fragment  {
    private final String TAG = "Tab1Fragment";
    Context ctx;
    private AdView mAdview;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    ImageView settings;
    Button btn;
    AutoCompleteTextView Name,Age,Height,Weight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        settings = (ImageView) view.findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mView = getLayoutInflater().inflate(R.layout.dialog_settings, null);

                spinner = (Spinner) mView.findViewById(R.id.setting_gender);
                adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Gender, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                mBuilder.setView(mView);
                final AlertDialog dialog=mBuilder.create();
                dialog.show();

                btn=(Button)mView.findViewById(R.id.setting_done);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"We are all set then!",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
            }
        });

        return view;
    }



    }

