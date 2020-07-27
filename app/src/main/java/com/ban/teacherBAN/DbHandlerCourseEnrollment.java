package com.ban.teacherBAN;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DbHandlerCourseEnrollment {
    private String mode;
    private DatabaseReference enrollmentMode;

    public String getEnrollmentMode(){
        enrollmentMode =  FirebaseDatabase.getInstance().getReference("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/a5_courseEnrollmentMode");
        enrollmentMode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    mode = dataSnapshot.getValue(String.class);
                    System.out.println("Inside mode: "+mode);
                }catch (Exception e){
                    mode = "Not Given";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mode = "Wait";
            }
        });
        System.out.println("Return mode: "+mode);
        return mode;
    }

}
