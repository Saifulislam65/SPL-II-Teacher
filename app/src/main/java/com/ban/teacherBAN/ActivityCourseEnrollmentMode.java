package com.ban.teacherBAN;

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

public class ActivityCourseEnrollmentMode extends AppCompatActivity {
    private TextView enrollMode;
    private Button activate;
    private String mode = "0";
    private int status;
    private DbHandlerCourseEnrollment dbHandlerCourseEnrollment;
    private DatabaseReference enrollmentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment_mode);
        enrollMode = findViewById(R.id.course_enrollment_status);
        activate = findViewById(R.id.enroll_activate_button);

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrollmentMode = FirebaseDatabase.getInstance().getReference("Course/" + ActivityInsideCourse.courseCodeForQrGenerator + "/a5_courseEnrollmentMode");
                enrollmentMode.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            status = dataSnapshot.getValue(Integer.class);
                            System.out.println("Inside mode: " + status);
                        } catch (Exception e) {
                            mode = "Not Given";
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mode = "Wait";
                    }
                });
                if (status == 1) {
                    activate.setBackgroundResource(R.drawable.round_background_ash);
                    activate.setText("START");
                    enrollmentMode.setValue(0);
                    enrollMode.setText("OFF");
                } else if (status == 0) {
                    activate.setBackgroundResource(R.drawable.round_background);
                    activate.setText("STOP");
                    enrollmentMode.setValue(1);
                    enrollMode.setText("ON");
                } else {
                    System.out.println("Enrollment status update error");
                    Toast.makeText(getApplicationContext(), "Can not connect to db", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    protected void onStart(){
        super.onStart();
        System.out.println("ON START mdoe:");
        /*activate.setBackgroundResource(R.drawable.round_background_ash);
        activate.setText("START");
        enrollMode.setText("OFF");*/
        enrollmentMode = FirebaseDatabase.getInstance().getReference("Course/" + ActivityInsideCourse.courseCodeForQrGenerator + "/a5_courseEnrollmentMode");
        enrollmentMode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    status = dataSnapshot.getValue(Integer.class);
                    System.out.println("Inside mode Onstart: " + status);
                    if (status == 0) {
                        activate.setBackgroundResource(R.drawable.round_background_ash);
                        activate.setText("START");
                        enrollMode.setText("OFF");
                    } else if (status == 1) {
                        activate.setBackgroundResource(R.drawable.round_background);
                        activate.setText("STOP");
                        enrollMode.setText("ON");
                    } else {
                        System.out.println("Enrollment status update error");
                        Toast.makeText(getApplicationContext(), "Can not connect to db", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    mode = "Not Given";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mode = "Wait";
            }
        });
        System.out.println("Status: "+status);

    }

    @Override
    protected void onPause() {
        super.onPause();
       /* enrollmentMode = FirebaseDatabase.getInstance().getReference("Course/" + ActivityInsideCourse.courseCodeForQrGenerator + "/a5_courseEnrollmentMode");
        enrollmentMode.setValue(0);*/
    }
}

