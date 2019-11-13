package com.ban.teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FragmentTeacherProfile extends Fragment {

    DatabaseReference teacherProfileData, teacherProfileDataInCOurse;
    FirebaseUser firebaseUser;
    TextView textViewEmail;
    EditText teacherName, teacherInstitute;
    Button update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_teacher_profile, container, false);
        textViewEmail = view.findViewById(R.id.teachers_profile_email);
        teacherName = view.findViewById(R.id.teachers_profile_name);
        teacherInstitute = view.findViewById(R.id.teachers_profile_institute);
        update = view.findViewById(R.id.teachers_profile_button_update);

        String key = showUserEmail();
        teacherProfileData = FirebaseDatabase.getInstance().getReference("Teacher/"+key+"/PersonalInfo");

        teacherProfileData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                        ListTeacherProfileInfo listTeacherProfileInfo = dataSnapshot.getValue(ListTeacherProfileInfo.class);
                        teacherName.setText(listTeacherProfileInfo.getTeacherName());
                        teacherInstitute.setText(listTeacherProfileInfo.getTeacherInstitute());

                    }catch (Exception e){
                        Toast.makeText(getContext(), "Ops...something is wrong!", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Ops...something is wrong!", Toast.LENGTH_LONG);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListTeacherProfileInfo info = new ListTeacherProfileInfo(
                        textViewEmail.getText().toString(),
                        teacherName.getText().toString(),
                        teacherInstitute.getText().toString()
                );

                teacherProfileData.setValue(info);
            }
        });


        return view;
    }

    private String showUserEmail() {
        String  email = "User E-mail";
        String providerId = null;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            email = firebaseUser.getEmail();
            textViewEmail.setText(email);
            providerId = firebaseUser.getUid();
            System.out.println("Inside Teacher Profile: "+providerId);
        }
        return providerId;
    }

}
