package com.ban.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class FragmentDashboard extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_fragment_dashboard, container, false);
        CardView card_assignment = view.findViewById(R.id.card_assignment);
        CardView card_marks = view.findViewById(R.id.card_marks);
        CardView card_qrCode = view.findViewById(R.id.card_QR_Code);
        CardView card_enroll = view.findViewById(R.id.card_enrollment);

        card_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getActivity(), ActivityAssignment.class);
                intent.putExtra("CoursePath", ActivityInsideCourse.coursePath);
                startActivity(intent);*/
                Toast.makeText(getContext(), "Coming Soon :)",Toast.LENGTH_LONG ).show();
            }
        });

        card_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), ActivityMarks.class);
                startActivity(intent);*/
                Toast.makeText(getContext(), "Coming Soon :)",Toast.LENGTH_LONG ).show();

            }
        });

        card_qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityQRcodeGenerator.class);
                startActivity(intent);

            }
        });

        card_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityCourseEnrollmentMode.class);
                startActivity(intent);

            }
        });

        return view;
    }

}
