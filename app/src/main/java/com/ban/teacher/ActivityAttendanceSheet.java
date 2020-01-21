package com.ban.teacher;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityAttendanceSheet extends AppCompatActivity {
    Button next, back, update;
    TextView studentInfo, Id;
    DatabaseReference databaseReference, getID, individualAttendanceSheet;
    ArrayList<ListSetStudentnCourseRepo> arrayList;
    ArrayList<ListAttendance>  attendanceSheetArrayList;
    AdapterAttendanceList adapterAttendanceList;
    TextView progress;
    String attendanceProgress;
    RecyclerView recyclerView;
    int attendanceCounter = 0;
    String getID_path;
    int i = 0;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_sheet);
        studentInfo = findViewById(R.id.student_info);
        Id = findViewById(R.id.student_id);
        next = findViewById(R.id.button_next);
        back = findViewById(R.id.button_back);
        progress = findViewById(R.id.attendance_progress);
        recyclerView = findViewById(R.id.attendance_list_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //update = findViewById(R.id.marks_update);

        final String courseKey = ActivityInsideCourse.courseCodeForQrGenerator;
        arrayList = new ArrayList<ListSetStudentnCourseRepo>();
        attendanceSheetArrayList = new ArrayList<ListAttendance>();

        getID = FirebaseDatabase.getInstance().getReference();


        databaseReference = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/attendance/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    System.out.println("Parent: "+dataSnapshot);
                    try {
                        String uid = dataSnapshot1.child("uid").getValue(String.class);
                        String studentEmail = dataSnapshot1.child("studentMail").getValue(String.class);

                        ListSetStudentnCourseRepo listSetStudentnCourseRepo = new ListSetStudentnCourseRepo(studentEmail, uid);
                        arrayList.add(listSetStudentnCourseRepo);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Ops...something is wrong!", Toast.LENGTH_LONG).show();
                    }

                }

                try{
                    studentInfo.setText(arrayList.get(i).getStudentMail());
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()!= 0){
                    if(i<arrayList.size()-1)
                        i++;
                    else if(i==arrayList.size()-1)
                        i = 0;
                    studentInfo.setText(arrayList.get(i).getStudentMail());
                    getID_path = "Student/"+arrayList.get(i).getUid()+"/PersonalInfo/";
                    getID = FirebaseDatabase.getInstance().getReference(getID_path);
                    getID.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            System.out.println("Get ID Data Snapshot: "+dataSnapshot);
                            try{
                                id = dataSnapshot.child("studentID").getValue(String.class);
                            }catch (Exception e){
                                id = "NULL";
                            }
                            Id.setText(id);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                    individualAttendanceSheet = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/attendance/"+arrayList.get(i).getUid()+"/sheet/");
                    individualAttendanceSheet.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            attendanceSheetArrayList.clear();
                            attendanceCounter = 0;
                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                String attendanceStatus = dataSnapshot1.getValue(String.class);
                                String date = dataSnapshot1.getKey();
                                attendanceCounter = attendanceCounter + Integer.parseInt(attendanceStatus);
                                ListAttendance listAttendance = new ListAttendance(date, attendanceStatus);
                                attendanceSheetArrayList.add(listAttendance);
                                System.out.println("Date: "+listAttendance.getDate());
                                System.out.println("Attendance Status: "+listAttendance.getAttendanceStatus());
                            }
                            adapterAttendanceList = new AdapterAttendanceList(getApplicationContext(), attendanceSheetArrayList);
                            recyclerView.setAdapter(adapterAttendanceList);
                            if(attendanceSheetArrayList.size()!= 0){
                                attendanceProgress = Integer.toString((attendanceCounter*100)/attendanceSheetArrayList.size());
                                progress.setText(attendanceProgress+"%");
                            }
                            else
                                progress.setText("NULL");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    studentInfo.setText("NULL2");
                    Toast.makeText(getApplicationContext(), "No student selected!", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()!= 0){
                    if(i>0)
                        i--;
                    else if(i==0)
                        i = arrayList.size()-1;

                    studentInfo.setText(arrayList.get(i).getStudentMail());
                    getID_path = "Student/"+arrayList.get(i).getUid()+"/PersonalInfo/";
                    getID = FirebaseDatabase.getInstance().getReference(getID_path);
                    getID.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            System.out.println("Get ID Data Snapshot: "+dataSnapshot);
                            try{
                                id = dataSnapshot.child("studentID").getValue(String.class);
                            }catch (Exception e){
                                id = "NULL";
                            }
                            Id.setText(id);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    individualAttendanceSheet = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/attendance/"+arrayList.get(i).getUid()+"/sheet/");
                    individualAttendanceSheet.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            attendanceSheetArrayList.clear();
                            attendanceCounter = 0;
                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                String attendanceStatus = dataSnapshot1.getValue(String.class);
                                String date = dataSnapshot1.getKey();
                                attendanceCounter = attendanceCounter + Integer.parseInt(attendanceStatus);
                                ListAttendance listAttendance = new ListAttendance(date, attendanceStatus);
                                attendanceSheetArrayList.add(listAttendance);
                                System.out.println("Date: "+listAttendance.getDate());
                                System.out.println("Attendance Status: "+listAttendance.getAttendanceStatus());
                            }
                            adapterAttendanceList = new AdapterAttendanceList(getApplicationContext(), attendanceSheetArrayList);
                            recyclerView.setAdapter(adapterAttendanceList);
                            if(attendanceSheetArrayList.size()!= 0){
                                attendanceProgress = Integer.toString((attendanceCounter*100)/attendanceSheetArrayList.size());
                                progress.setText(attendanceProgress+"%");
                            }
                            else
                                progress.setText("NULL");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    studentInfo.setText("NULL");
                    Toast.makeText(getApplicationContext(), "No student selected!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
