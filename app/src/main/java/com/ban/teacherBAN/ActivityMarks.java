package com.ban.teacherBAN;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityMarks extends AppCompatActivity {
    Button next, back, update;
    EditText q1, q2, q3, q4, m, f;
    TextView studentInfo, Id;
    ProgressBar progressBar;
    DatabaseReference databaseReference, databaseReferenceSetMarks, getMarks, getID;
    ArrayList<ListSetStudentnCourseRepo> arrayList ;
    ArrayList<String> arrayID;
    String getID_path;
    int i = 0, j;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        progressBar = findViewById(R.id.progressbar);
        studentInfo = findViewById(R.id.student_info);
        Id = findViewById(R.id.student_id);
        next = findViewById(R.id.button_next);
        back = findViewById(R.id.button_back);
        update = findViewById(R.id.marks_update);
        q1 = findViewById(R.id.marks_quiz1);
        q2 = findViewById(R.id.marks_quiz2);
        q3 = findViewById(R.id.marks_quiz3);
        q4 = findViewById(R.id.marks_quiz4);
        m = findViewById(R.id.marks_mid);
        f = findViewById(R.id.marks_final);

        final String courseKey = ActivityInsideCourse.courseCodeForQrGenerator;
        arrayList = new ArrayList<ListSetStudentnCourseRepo>();
        arrayID = new ArrayList<String>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/marks/");
        getID = FirebaseDatabase.getInstance().getReference();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setIndeterminate(true);

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

                progressBar.setVisibility(View.GONE);
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



                   getMarks = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/marks/"+arrayList.get(i).getUid()+"/sheet/");
                   getMarks.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           ListMarks listMarks = dataSnapshot.getValue(ListMarks.class);
                          try{
                              q1.setText(listMarks.getQuiz1());
                              q2.setText(listMarks.getQuiz2());
                              q3.setText(listMarks.getQuiz3());
                              q4.setText(listMarks.getQuiz4());
                              m.setText(listMarks.getMid());
                              f.setText(listMarks.getSemFinal());
                          }catch (NullPointerException e){
                              Toast.makeText(getApplicationContext(), "Null Pointer", Toast.LENGTH_LONG).show();
                          }
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
                    getMarks = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/marks/"+arrayList.get(i).getUid()+"/sheet/");
                    getMarks.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ListMarks listMarks = dataSnapshot.getValue(ListMarks.class);
                            try{
                                q1.setText(listMarks.getQuiz1());
                                q2.setText(listMarks.getQuiz2());
                                q3.setText(listMarks.getQuiz3());
                                q4.setText(listMarks.getQuiz4());
                                m.setText(listMarks.getMid());
                                f.setText(listMarks.getSemFinal());
                            }catch (NullPointerException e){
                                Toast.makeText(getApplicationContext(), "Null Pointer", Toast.LENGTH_LONG).show();
                            }
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(arrayList.size()!=0){
                   ListMarks listMarks = new ListMarks(
                           q1.getText().toString(),
                           q2.getText().toString(),
                           q3.getText().toString(),
                           q4.getText().toString(),
                           m.getText().toString(),
                           f.getText().toString()
                   );

                   databaseReferenceSetMarks = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/marks/"+arrayList.get(i).getUid()+"/sheet/");

                   databaseReferenceSetMarks.setValue(listMarks);

                   Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_LONG).show();
               }else{
                   studentInfo.setText("NULL");
                   Toast.makeText(getApplicationContext(), "No student selected!", Toast.LENGTH_LONG).show();
               }

           }
        });

    }
}