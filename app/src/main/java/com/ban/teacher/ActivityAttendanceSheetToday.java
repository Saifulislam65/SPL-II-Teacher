package com.ban.teacher;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
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

public class ActivityAttendanceSheetToday extends AppCompatActivity {
    private TextView date,day;
    private DatabaseReference studentList, getStudentInfo, getAttendanceStatus;
    private ArrayList<String> parentList , attendanceStatus;
    private ArrayList<ListStudentInfoForTodayAttendance> studentInfo;
    private AdapterAttendanceSheetToday attendanceSheetToday;
    RecyclerView recyclerView;
    int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_sheet_today);

        date = findViewById(R.id.date);
        date.setText(returnDate());

        day  = findViewById(R.id.day);
        day.setText(returnDay());

        recyclerView = findViewById(R.id.attendance_list_today_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        parentList = new ArrayList<String>();
        attendanceStatus = new ArrayList<String>();
        studentInfo = new ArrayList<ListStudentInfoForTodayAttendance>();

        studentList = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/marks");
        studentList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    try{
                        parentList.add(dataSnapshot1.getKey());
                        System.out.println(dataSnapshot1.getKey());
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "No Student Found!", Toast.LENGTH_LONG).show();
                    }

                }
                System.out.println("Parent Size: "+parentList.size());
                for(int i = 0; i<parentList.size(); i++){
                    getAttendanceStatus = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/attendance/"+parentList.get(i)+"/sheet/");
                    getAttendanceStatus.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try{
                                String status = dataSnapshot.child(returnDate()).getValue(String.class);

                                if(status == null){
                                    getAttendanceStatus.child(returnDate()).setValue("0");
                                    status = dataSnapshot.child(returnDate()).getValue(String.class);
                                    System.out.println("Status: "+status);
                                }

                                attendanceStatus.add(status);
                            }catch (Exception e){
                                getAttendanceStatus.child(returnDate()).setValue("0");
                                attendanceStatus.add("0");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    getStudentInfo = FirebaseDatabase.getInstance().getReference().child("Student/"+parentList.get(i)+"/PersonalInfo");
                    getStudentInfo.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           try{
                               ListStudentInfoForTodayAttendance todayAttendance = new ListStudentInfoForTodayAttendance();

                               String uid = dataSnapshot.child("studentID").getValue(String.class);
                               String name = dataSnapshot.child("studentName").getValue(String.class);

                               todayAttendance.setStudentName(name);
                               todayAttendance.setStudentID(uid);

                               studentInfo.add(todayAttendance);

                           }catch (Exception e){
                               System.out.println("Error: "+e.getMessage());
                           }
                            try{
                                attendanceSheetToday = new AdapterAttendanceSheetToday(getApplicationContext(), studentInfo, attendanceStatus);
                                recyclerView.setAdapter(attendanceSheetToday);
                                System.out.println("ID: "+studentInfo.get(j).getStudentID()+" Name: "+studentInfo.get(j).getStudentName()+" Status: "+attendanceStatus.get(j));
                                j++;
                            }catch (Exception e){
                                System.out.println(""+e.getMessage());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Ops...something is wrong!", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Ops...something is wrong!", Toast.LENGTH_LONG).show();
            }
        });

    }

    public String returnDate(){
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");//formating according to my need
        String date = formatter.format(today);
        return date;
    }

    public String returnDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }
}
