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

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Tab1_TabbedSpace extends Fragment {
    private final String TAG = "Tab1Fragment";
    Context ctx;
    private AdView mAdview;
    Spinner spinner,workout;
    ArrayAdapter<CharSequence> adapter,adapter1;
    ImageView settings;
    Button btn;
    AutoCompleteTextView Name, Age, Height, Weight,Days;
    FirebaseAuth auth;
    FirebaseUser user;

    //LoginButton loginButton;

    CallbackManager callbackManager;

   public Tab1_TabbedSpace(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        settings = (ImageView) view.findViewById(R.id.settings);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("App Name");
        myRef.setValue("GymTracker");



        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mView = getLayoutInflater().inflate(R.layout.dialog_settings, null);

                spinner = (Spinner) mView.findViewById(R.id.setting_gender);
                adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Gender, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                workout = (Spinner) mView.findViewById(R.id.setting_workout);
                adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.workout, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                workout.setAdapter(adapter1);

                Name = (AutoCompleteTextView) mView.findViewById(R.id.setting_name);
                Age = (AutoCompleteTextView) mView.findViewById(R.id.setting_age);
                Height = (AutoCompleteTextView) mView.findViewById(R.id.setting_height);
                Weight = (AutoCompleteTextView) mView.findViewById(R.id.setting_weight);
                Days = (AutoCompleteTextView) mView.findViewById(R.id.setting_Goal);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                btn = (Button) mView.findViewById(R.id.setting_done);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = Name.getText().toString();
                        String gender = spinner.getSelectedItem().toString();
                        String work = workout.getSelectedItem().toString();

                        try {



                            String a = Age.getText().toString();
                            int age = Integer.parseInt(a);

                            String b = Height.getText().toString();
                            Float height = Float.parseFloat(b);

                            String c = Weight.getText().toString();
                            int weight = Integer.parseInt(c);

                            String d = Days.getText().toString();
                            int day = Integer.parseInt(d);


                        } catch (Exception e) {

                        } finally {
                            Toast.makeText(getActivity(), work, Toast.LENGTH_SHORT).show();

                            dialog.cancel();
                        }

                    }
                });
            }
        });

        return view;
    }


}

