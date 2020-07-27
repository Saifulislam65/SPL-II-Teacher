package com.ban.teacherBAN;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
    ArrayList<ListSetStudentnCourseRepo> arrayList  ;
    ArrayList<String> singleValue;
    DatabaseReference databaseReference, getAttendance;
    private DbHandlerFetchStudentInfo dbHandlerFetchStudentInfo;
    private DbHandlerAttendanceInfo dbHandlerAttendanceInfo;
    Spinner spinner;
    int i = 0;
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
        spinner = findViewById(R.id.dropDown);

        date = findViewById(R.id.manual_attendance_date);
        day = findViewById(R.id.manual_attendance_day);
        attendanceStatus = findViewById(R.id.manual_attendance_status);

        setDate(date);
        setDay(day);

        final String courseKey = ActivityInsideCourse.courseCodeForQrGenerator;
        arrayList = new ArrayList<ListSetStudentnCourseRepo>();
        singleValue = new ArrayList<String>();

        dbHandlerFetchStudentInfo = new DbHandlerFetchStudentInfo();
        dbHandlerAttendanceInfo = new DbHandlerAttendanceInfo();

        databaseReference = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/attendance/");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList.clear();
                singleValue.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    System.out.println("Parent: "+dataSnapshot);
                    try {
                        String uid = dataSnapshot1.child("uid").getValue(String.class);
                        String studentEmail = dataSnapshot1.child("studentMail").getValue(String.class);

                        ListSetStudentnCourseRepo listSetStudentnCourseRepo = new ListSetStudentnCourseRepo(studentEmail, uid);
                        arrayList.add(listSetStudentnCourseRepo);
                        singleValue.add(studentEmail);
                        studentInfo.setText(arrayList.get(i).getStudentMail());
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Ops...something is wrong!", Toast.LENGTH_LONG).show();
                    }

                }
                try{
                    if(!arrayList.isEmpty()){
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, singleValue);
                        spinner.setAdapter(dataAdapter);
                        //i=spinner.getSelectedItemPosition();
                    }
                }catch (NullPointerException e){

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
                    id = dbHandlerFetchStudentInfo.getSID(arrayList.get(i).uid);
                    System.out.println("i: "+i+" id: "+id+" email: "+arrayList.get(i).getStudentMail()+" UID: "+arrayList.get(i).getUid());
                    setID(id);

                    System.out.println("i: "+i);
                    String attendance = dbHandlerAttendanceInfo.getSingleDateAttendance(courseKey,arrayList.get(i).getUid());
                    attendanceStatus.setText(attendance);

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

                    System.out.println("i: "+i);
                    studentInfo.setText(arrayList.get(i).getStudentMail());
                    id = dbHandlerFetchStudentInfo.getSID(arrayList.get(i).uid);
                    setID(id);
                    String attendance = dbHandlerAttendanceInfo.getSingleDateAttendance(courseKey,arrayList.get(i).getUid());
                    attendanceStatus.setText(attendance);

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

                    dbHandlerAttendanceInfo.setAttendance(courseKey,arrayList.get(i).getUid(), "1" );
                    attendanceStatus.setText("1");

                    String attendance = dbHandlerAttendanceInfo.getSingleDateAttendance(courseKey,arrayList.get(i).getUid());
                    attendanceStatus.setText(attendance);

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
                   // i=spinner.getSelectedItemPosition();

                    dbHandlerAttendanceInfo.setAttendance(courseKey,arrayList.get(i).getUid(), "0" );
                    attendanceStatus.setText("0");

                    String attendance = dbHandlerAttendanceInfo.getSingleDateAttendance(courseKey,arrayList.get(i).getUid());
                    attendanceStatus.setText(attendance);

                }else{
                    studentInfo.setText("NULL");
                    Toast.makeText(getApplicationContext(), "No student selected!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    public void setDate (TextView view){

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");//formating according to my need
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");//formating according to my need
        String date = formatter.format(today);
        return date;
    }

    private void setID(String value){
        if(value==""){
            Id.setText("Roll not given");
        }else{
            Id.setText(id);
        }
    }

}
