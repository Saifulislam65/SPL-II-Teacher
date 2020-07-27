package com.ban.teacherBAN;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityAssignment extends AppCompatActivity {
    EditText assigmentTitle, assignmentDetail;
    Button assign;
    DatabaseReference assignment;
    String assignmentPath;
    ArrayList<ListAssignment> assignmentList;
    RecyclerView recyclerView;
    AdapterSearchAssignment adapterSearchAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        assigmentTitle = findViewById(R.id.assignment_title);
        assignmentDetail = findViewById(R.id.assignment_detail);
        assign = findViewById(R.id.assignment_asign);
        recyclerView = findViewById(R.id.recycleview_assignment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        assignmentList = new ArrayList<ListAssignment>();
        assignmentPath = getCoursePath() + "/assignTask";
        assignment = FirebaseDatabase.getInstance().getReference().child(assignmentPath);



        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = assignment.push().getKey();
                AdapterCreateAssignment createAssignment = new AdapterCreateAssignment(
                        assigmentTitle.getText().toString(),
                        assignmentDetail.getText().toString()
                );
                assignment.child(key).setValue(createAssignment);

            }
        });


        assignment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // progressBar.setIndeterminate(true);

                assignmentList.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    try{
                        ListAssignment listCourse = dataSnapshot1.getValue(ListAssignment.class);
                        assignmentList.add(listCourse);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Ops...something is wrong!", Toast.LENGTH_LONG);
                    }

                }
                adapterSearchAssignment = new AdapterSearchAssignment(getApplicationContext(), assignmentList);
                recyclerView.setAdapter(adapterSearchAssignment);

                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Ops...something is wrong!", Toast.LENGTH_LONG);
            }
        });

    }

    private String getCoursePath() {
        String coursePath =null;
        Bundle extras = getIntent().getExtras();
        coursePath = extras.getString("CoursePath");
        return coursePath;
    }
}
