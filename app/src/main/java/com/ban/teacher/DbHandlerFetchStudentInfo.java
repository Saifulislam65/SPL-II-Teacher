package com.ban.teacher;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DbHandlerFetchStudentInfo {
    private String SID;
    private DatabaseReference studentData;


    public String getSID(String studentEmailUID){
        studentData =  FirebaseDatabase.getInstance().getReference("Student/"+studentEmailUID+"/PersonalInfo/");
        studentData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    SID = dataSnapshot.child("studentID").getValue(String.class);
                }catch (Exception e){
                    SID = "ID Not Set";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return SID;
    }

}
