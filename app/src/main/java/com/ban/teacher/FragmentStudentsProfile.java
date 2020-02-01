package com.ban.teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class FragmentStudentsProfile extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference studentList, getStudentInfo;
    private ArrayList<String> parentList ;
    private ArrayList<ListStudentInfoForTodayAttendance> studentInfo;
    private AdapterAttendanceSheetToday attendanceSheetToday;
    int j = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_students_profile, container, false);
        recyclerView = view.findViewById(R.id.student_profile_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        parentList = new ArrayList<String>();
        studentInfo = new ArrayList<ListStudentInfoForTodayAttendance>();

        studentList = FirebaseDatabase.getInstance().getReference().child("Course/"+ActivityInsideCourse.courseCodeForQrGenerator+"/marks");
        studentList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parentList.clear();
                studentInfo.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    try{
                        parentList.add(dataSnapshot1.getKey());
                        System.out.println(dataSnapshot1.getKey());
                    }catch (Exception e){
                        Toast.makeText(getContext(), "No Student Found!", Toast.LENGTH_LONG).show();
                    }

                }
                System.out.println("Parent Size: "+parentList.size());
                for(int i = 0; i<parentList.size(); i++){
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
                                Collections.sort(studentInfo, ListStudentInfoForTodayAttendance.sortByStudentID);
                                attendanceSheetToday = new AdapterAttendanceSheetToday(getContext(), studentInfo);
                                recyclerView.setAdapter(attendanceSheetToday);
                                System.out.println("ID: "+studentInfo.get(j).getStudentID()+" Name: "+studentInfo.get(j).getStudentName());
                                j++;
                            }catch (Exception e){
                                System.out.println(""+e.getMessage());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Ops...something is wrong!", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Ops...something is wrong!", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
