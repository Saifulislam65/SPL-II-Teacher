package com.ban.teacher;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
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
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class ActivityAttendanceSheetToday extends AppCompatActivity {
    private TextView date,day, totalStudent;
    private DatabaseReference studentList, getStudentInfo, getAttendanceStatus;
    private ArrayList<String> parentList ;
    private ArrayList<String> attendanceStatus =new ArrayList<String>();
    ArrayList<ListStudentInfoForTodayAttendance> studentInfo = new ArrayList<ListStudentInfoForTodayAttendance>();
    private AdapterAttendanceSheetToday attendanceSheetToday;
    private DbHandlerAttendanceInitialization initialization;
    final Calendar myCalendar = Calendar.getInstance();
    RecyclerView recyclerView;
    Thread thread;
    int i = 0;
    int j = 0;
    int k = 0;
    String attendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_sheet_today);

        date = findViewById(R.id.date);
        date.setText(returnDate());
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityAttendanceSheetToday.this, datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        day  = findViewById(R.id.day);
        day.setText(returnDay());

        totalStudent  = findViewById(R.id.total_Student);
        updateStudentCount();


        recyclerView = findViewById(R.id.attendance_list_today_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        parentList = new ArrayList<String>();
        /*attendanceStatus = new ArrayList<String>();*/
       /* studentInfo = new ArrayList<ListStudentInfoForTodayAttendance>();*/

        studentList = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/attendance");
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
                for(i = 0; i<parentList.size(); i++){
                    getAttendanceStatus = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/attendance/"+parentList.get(i)+"/sheet/");
                    getAttendanceStatus.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try{

                                String status = dataSnapshot.child(date.getText().toString()).getValue(String.class);
                                if(status != null){
                                    attendanceStatus.add(status);
                                }

                                if(status.equals(null)){
                                    attendanceStatus.add("00");
                                }
                            }catch (Exception e){
                                System.out.println("inside catch");
                                attendanceStatus.add("00");
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
                               todayAttendance.setAttendanceStatus(attendanceStatus.get(k));
                               System.out.println("attendance set "+attendanceStatus.get(k));
                               k++;

                               studentInfo.add(todayAttendance);

                           }catch (Exception e){
                               System.out.println("Error: "+e.getMessage());
                           }

                            try{
                                Collections.sort(studentInfo, ListStudentInfoForTodayAttendance.sortByStudentID);
                                attendanceSheetToday = new AdapterAttendanceSheetToday(getApplicationContext(), studentInfo);
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

        System.out.println("attendance size: "+attendanceStatus.size());
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

    private void updateLabel() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");

        date.setText(sdf.format(myCalendar.getTime()));
        day.setText(sdf2.format(myCalendar.getTime()));

        updateAttendanceStatus();
        updateStudentCount();
    }

    private void updateAttendanceStatus(){
        k=0;
        studentList = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/marks");
        studentList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parentList.clear();
                studentInfo.clear();
                attendanceStatus.clear();
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
                                String status = dataSnapshot.child(date.getText().toString()).getValue(String.class);
                                if(status != null){
                                    attendanceStatus.add(status);
                                }

                                if(status.equals(null)){
                                    attendanceStatus.add("00");}
                            }catch (Exception e){
                                System.out.println("inside catch");
                                attendanceStatus.add("00");
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
                                todayAttendance.setAttendanceStatus(attendanceStatus.get(k));
                                System.out.println("attendance set "+attendanceStatus.get(k));
                                k++;
                                studentInfo.add(todayAttendance);

                            }catch (Exception e){
                                System.out.println("Error: "+e.getMessage());
                            }

                            try{
                                Collections.sort(studentInfo, ListStudentInfoForTodayAttendance.sortByStudentID);
                                attendanceSheetToday = new AdapterAttendanceSheetToday(getApplicationContext(), studentInfo);
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

    private void updateStudentCount(){
        initialization = new DbHandlerAttendanceInitialization();
        thread = new Thread() {
            @Override
            public void run() {
                int i = 0;
                try {
                    while(true) {
                        i++;
                        if(i == 5){
                            thread.interrupt();
                            break;
                        }

                        sleep(2000);
                        totalStudent.setText(Integer.toString(initialization.attendanceCount(date.getText().toString())));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            thread.interrupt();
            finish();
        }catch (Exception e){
            finish();
            thread.interrupt();
        }

    }


}
