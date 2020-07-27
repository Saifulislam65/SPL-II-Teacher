package com.ban.teacherBAN;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DbHandlerAttendanceInfo {
    private String singleDateAttendance;
    private DatabaseReference attendanceData;

    public String getSingleDateAttendance(String courseUID, String studentEmailUID){
        attendanceData =  FirebaseDatabase.getInstance().getReference("Course/"+courseUID+"/attendance/"+studentEmailUID+"/sheet/"+returnDate());
        attendanceData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    singleDateAttendance = dataSnapshot.getValue(String.class);
                }catch (Exception e){
                    singleDateAttendance = "Not Given";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                singleDateAttendance = "Wait";
            }
        });
        return singleDateAttendance;
    }

    public void setAttendance(String courseUID, String studentEmailUID, String attendanceStatus){
        attendanceData =  FirebaseDatabase.getInstance().getReference("Course/"+courseUID+"/attendance/"+studentEmailUID+"/sheet/"+returnDate());
        attendanceData.setValue(attendanceStatus);
    }

    public String returnDate(){
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");//formating according to my need
        String date = formatter.format(today);
        return date;
    }
}
