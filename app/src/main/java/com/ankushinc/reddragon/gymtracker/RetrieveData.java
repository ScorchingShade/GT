package com.ankushinc.reddragon.gymtracker;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RetrieveData {

    public RetrieveData() {
    }

    public RetrieveData(String Name,int age, int weight,float height,int days,String workout,String Gender){

    }



    public void retrieveDetails(View mView,DatabaseReference mDatabase){

        String key = mDatabase.child("USERS").push().getKey();

        DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("USERS");
        String mGroupId = readRef.push().getKey();

        readRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds :dataSnapshot.getChildren())
                RetrieveData retrieveData= ds.getValue(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
