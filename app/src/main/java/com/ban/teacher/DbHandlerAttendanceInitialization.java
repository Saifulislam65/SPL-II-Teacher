package com.ban.teacher;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DbHandlerAttendanceInitialization {
    private DatabaseReference studentList,getAttendanceStatus;
    private ArrayList<String> parentList ;
    int i;
    int count = 0;
    public void initialization(){
        parentList = new ArrayList<String>();
        studentList = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/attendance");
        studentList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    try{
                        parentList.add(dataSnapshot1.getKey());
                        System.out.println(dataSnapshot1.getKey());
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("Parent Size: "+parentList.size());
                for(i = 0; i<parentList.size(); i++){
                    getAttendanceStatus = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/attendance/"+parentList.get(i)+"/sheet/");
                    if(!dataSnapshot.child(parentList.get(i)).child("sheet").hasChild(returnDate()))
                        getAttendanceStatus.child(returnDate()).setValue("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public int attendanceCount(final String date){
        i = 0;
        parentList = new ArrayList<String>();
        studentList = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/attendance");
        studentList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    try{
                        parentList.add(dataSnapshot1.getKey());
                    }catch (Exception e){
                    }

                }
                for(int i = 0; i<parentList.size(); i++){
                    if(i == 0)
                        count = 0;
                    getAttendanceStatus = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/attendance/"+parentList.get(i)+"/sheet/");
                    getAttendanceStatus.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try{
                                String status = dataSnapshot.child(date).getValue(String.class);
                                System.out.print(status+" ");
                                count = count + Integer.parseInt(status);
                            }catch (Exception e){
                                count = count + 0;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("Count: "+count);
        return count;
    }

    public String returnDate(){
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");//formating according to my need
        String date = formatter.format(today);
        return date;
    }
}
