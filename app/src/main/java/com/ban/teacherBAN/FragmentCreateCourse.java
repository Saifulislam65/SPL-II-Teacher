package com.ban.teacherBAN;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FragmentCreateCourse extends Fragment {
    private DatabaseReference databaseReference, setInTeacherRepo, teacherProfileInCourse;
    FirebaseUser firebaseUser;
    private EditText courseName, courseCode, courseClass;
    private Button createCourse;
    ProgressBar progressBar;
    String courseStore;
    ListTeacherProfileInfo teacherProfileInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_create_course, container, false);
        courseName = view.findViewById(R.id.create_course_name);
        courseCode = view.findViewById(R.id.create_course_code);
        courseClass = view.findViewById(R.id.create_course_class);
        createCourse = view.findViewById(R.id.create_course);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        courseStore = showUid();


        createCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(courseName.getText().toString().length()>2  && courseCode.getText().toString().length()>3){
                    createCourseMethod();
                }else {

                    Toast.makeText(getContext(), "Invalid Field!", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    private void createCourseMethod() {
        //progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("Course");

        String key = databaseReference.push().getKey();
        AdapterCreateCourse createCourse = new AdapterCreateCourse(
                courseName.getText().toString(),
                courseCode.getText().toString(),
                courseClass.getText().toString(),
                "Teacher ID",
                0
        );
        databaseReference.child(key).setValue(createCourse);

        setInTeacherRepo = FirebaseDatabase.getInstance().getReference("Teacher/"+courseStore+"/"+"CourseList/"+key);
        ListCourse course = new ListCourse(courseName.getText().toString(),  courseCode.getText().toString());
        setInTeacherRepo.setValue(course);


        teacherProfileInCourse= FirebaseDatabase.getInstance().getReference("Course/"+key+"/a4_teacherID");
        ListSetTeacherID listSetTeacherID = new ListSetTeacherID(courseStore);
        teacherProfileInCourse.setValue(listSetTeacherID);

        Toast.makeText(getContext(), "Course Created.", Toast.LENGTH_LONG).show();
        cleanField();
        progressBar.setVisibility(View.GONE);
    }

    private void cleanField() {
        courseName.setText("");
        courseCode.setText("");
        courseClass.setText("");
    }

    private String showUid() {
        String Uid = null;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Uid = firebaseUser.getUid();
            System.out.println("Inside Create Course: "+Uid);
        }
        return Uid;
    }

}
