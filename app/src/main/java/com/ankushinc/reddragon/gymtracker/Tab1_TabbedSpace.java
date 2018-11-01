package com.ankushinc.reddragon.gymtracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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
import static java.lang.Thread.sleep;


public class Tab1_TabbedSpace extends Fragment {
    private final String TAG = "Tab1Fragment";

    //firebase
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase,user,data;
    FirebaseAuth auth;


    //app
    Context ctx;
    private AdView mAdview;
    Spinner spinner, workout;
    ArrayAdapter<CharSequence> adapter, adapter1;
    ImageView settings, signout,cancel;
    Button btn;
    AutoCompleteTextView Name, Age, Height, Weight, Days;
    ProgressBar bar2,bar3,bar4;
    TextView homeName;

    int AgeV,WeightV,GoalDaysV;
    String NameV,WorkoutV,GenderV;
    Float HeightV;


    //LoginButton loginButton;
    CallbackManager callbackManager;

    public Tab1_TabbedSpace() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        //Firebase methods
        auth = FirebaseAuth.getInstance();
        // [START initialize_database_ref]
        firebaseDatabase=FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();


        settings = (ImageView) view.findViewById(R.id.settings);
        bar2=(ProgressBar) view.findViewById(R.id.progressBar2);
        bar3=(ProgressBar) view.findViewById(R.id.progressBar3);
        bar4=(ProgressBar) view.findViewById(R.id.progressBar4);



        final ProgressDialog progressDialog = new ProgressDialog(getActivity());


        signout = (ImageView) view.findViewById(R.id.signout_icon);



        progressDialog.setMessage("Signing out Please Wait...");
        progressDialog.show();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to Signout?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing, but close the dialog
                        auth.getInstance().signOut();
                        startActivity(new Intent(getActivity(),LoginActivity.class));
                        getFragmentManager().beginTransaction().remove(Tab1_TabbedSpace.this).commitAllowingStateLoss();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();



            }

        });
        progressDialog.dismiss();


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


                cancel=(ImageView)mView.findViewById(R.id.cancel_img_dialog);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });



                btn = (Button) mView.findViewById(R.id.setting_done);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = Name.getText().toString();
                        String gender = spinner.getSelectedItem().toString();
                        String work = workout.getSelectedItem().toString();
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        try {
                            String a = Age.getText().toString();
                            int age = Integer.parseInt(a);

                            String b = Height.getText().toString();
                            Float height = Float.parseFloat(b);

                            String c = Weight.getText().toString();
                            int weight = Integer.parseInt(c);

                            String d = Days.getText().toString();
                            int day = Integer.parseInt(d);


                            storeDetails(name,age,height,weight,gender,day,work,progressDialog);


                        } catch (Exception e) {

                        } finally {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"We are good to go "+name,Toast.LENGTH_SHORT).show();

                            dialog.cancel();

                        }


                    }
                });
            }
        });

        bar2.setMax(100);
        bar2.setProgress(5);
        homeName=(TextView)view.findViewById(R.id.home_name);

        return view;

    }

    public void storeDetails(String Name, int Age, float Height,int Weight, String Gender,int GoalDays,String WorkoutType,ProgressDialog prog){


        FirebaseDatabase database = FirebaseDatabase.getInstance();


        String key = mDatabase.child("users").push().getKey();

        mDatabase.child("USERS").child(key).child("Name").setValue(Name);
        mDatabase.child("USERS").child(key).child("Age").setValue(Age);
        mDatabase.child("USERS").child(key).child("Height").setValue(Height);
        mDatabase.child("USERS").child(key).child("Weight").setValue(Weight);
        mDatabase.child("USERS").child(key).child("Gender").setValue(Gender);
        mDatabase.child("USERS").child(key).child("GoalDays").setValue(GoalDays);
        mDatabase.child("USERS").child(key).child("WorkoutType").setValue(WorkoutType);

        prog.setMessage("Pumping Iron, just a sec now...");
        prog.show();

    }



}

