package com.ban.teacher;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ActivityManualAttendance extends AppCompatActivity {
    Button absent, present, next, back;
    TextView studentInfo, date, day, attendanceStatus, Id;
    ArrayList<ListSetStudentnCourseRepo> arrayList ;
    DatabaseReference databaseReference, databaseReferenceSetAttendance, getAttendance, getID;
    int i = 0;
    String getID_path;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_attendance);

        absent = findViewById(R.id.manual_attendance_absent);
        present = findViewById(R.id.manual_attendance_present);
        next = findViewById(R.id.manual_attendance_next);
        back = findViewById(R.id.manual_attendance_back);

        studentInfo = findViewById(R.id.manual_attendance_student_info);
        Id = findViewById(R.id.student_id);
        date = findViewById(R.id.manual_attendance_date);
        day = findViewById(R.id.manual_attendance_day);
        attendanceStatus = findViewById(R.id.manual_attendance_status);

        setDate(date);
        setDay(day);

        final String courseKey = ActivityInsideCourse.courseCodeForQrGenerator;
        arrayList = new ArrayList<ListSetStudentnCourseRepo>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/a5_studentList/");


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
                        studentInfo.setText(arrayList.get(i).getStudentMail());
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Ops...something is wrong!", Toast.LENGTH_LONG).show();
                    }

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

                    getAttendance = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/a5_studentList/"+arrayList.get(i).getUid()+"/attendance/"+returnDate());
                    getAttendance.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String attendance = dataSnapshot.getValue(String.class);
                            System.out.println("Attendance: "+ attendance);
                            attendanceStatus.setText(attendance);
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

                    getAttendance = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/a5_studentList/"+arrayList.get(i).getUid()+"/attendance/"+returnDate());
                    getAttendance.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String attendance = dataSnapshot.getValue(String.class);
                            System.out.println("Attendance: "+ attendance);
                            attendanceStatus.setText(attendance);
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

        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()!=0){

                    databaseReferenceSetAttendance = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/a5_studentList/"+arrayList.get(i).getUid()+"/attendance/"+returnDate());

                    databaseReferenceSetAttendance.setValue("1");

                    getAttendance = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/a5_studentList/"+arrayList.get(i).getUid()+"/attendance/"+returnDate());
                    getAttendance.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String attendance = dataSnapshot.getValue(String.class);
                            System.out.println("Attendance: "+ attendance);
                            setAttendanceStatus(attendanceStatus, attendance);
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

        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()!=0){

                    databaseReferenceSetAttendance = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/a5_studentList/"+arrayList.get(i).getUid()+"/attendance/"+returnDate());

                    databaseReferenceSetAttendance.setValue("0");

                    getAttendance = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/a5_studentList/"+arrayList.get(i).getUid()+"/attendance/"+returnDate());
                    getAttendance.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String attendance = dataSnapshot.getValue(String.class);
                            System.out.println("Attendance: "+ attendance);
                            setAttendanceStatus(attendanceStatus, attendance);
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
    public void setDate (TextView view){

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");//formating according to my need
        String date = formatter.format(today);
        view.setText(date);
    }

    public void setDay(TextView view){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        view.setText(dayOfTheWeek);
    }

    public String returnDate(){
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");//formating according to my need
        String date = formatter.format(today);
        return date;
    }

    public void setAttendanceStatus(TextView view, String status){
        if(status == "1")
            view.setText("Present");
        else if(status == "0")
            view.setText("Absent");
        else
            view.setText("NULL");

    }
}
