package com.ban.teacher;

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
    TextView studentInfo;
    ProgressBar progressBar;
    DatabaseReference databaseReference, databaseReferenceSetMarks;
    ArrayList<ListSetStudentnCourseRepo> arrayList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        progressBar = findViewById(R.id.progressbar);
        studentInfo = findViewById(R.id.student_info);
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
        databaseReference = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/a5_studentList/");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setIndeterminate(true);

                arrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    try {
                        ListSetStudentnCourseRepo listSetStudentnCourseRepo = dataSnapshot1.getValue(ListSetStudentnCourseRepo.class);
                        arrayList.add(listSetStudentnCourseRepo);
                        studentInfo.setText(arrayList.get(0).getStudentMail());
                        System.out.println(dataSnapshot1.getKey());
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


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListMarks listMarks = new ListMarks(
                        q1.getText().toString(),
                        q2.getText().toString(),
                        q3.getText().toString(),
                        q4.getText().toString(),
                        m.getText().toString(),
                        f.getText().toString()
                );

                databaseReferenceSetMarks = FirebaseDatabase.getInstance().getReference("Course/"+courseKey+"/a5_studentList/"+arrayList.get(0).getUid()+"/marks");
                System.out.println("Path: "+"Course/"+courseKey+"/a5_studentList/"+arrayList.get(0).getUid()+"/marks");



                try{
                    databaseReferenceSetMarks.setValue(listMarks);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
                }




           }
        });

    }
}