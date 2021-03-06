package com.ban.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class FragmentAttendance extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_fragment_attendance, container, false);

        CardView card_attendance_manual = view.findViewById(R.id.manual_attendance);
        CardView card_attendance_device = view.findViewById(R.id.device_attendance);
        CardView card_start_device_enrollment = view.findViewById(R.id.start_device_attendance);
        CardView card_attendance_sheet = view.findViewById(R.id.attendance_sheet);
        CardView card_attendance_sheet_today = view.findViewById(R.id.attendance_sheet_today);

        card_attendance_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), ActivityManualAttendance.class);
                startActivity(intent);*/
                Toast.makeText(getContext(), "Coming Soon :)",Toast.LENGTH_LONG ).show();
            }
        });

        card_attendance_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityDeviceAttendance.class);
                startActivity(intent);

            }
        });

        card_start_device_enrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), ActivityStartDeviceEnrollment.class);
                startActivity(intent);
            }
        });

        card_attendance_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), ActivityAttendanceSheet.class);
                startActivity(intent);
            }
        });
        card_attendance_sheet_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), ActivityAttendanceSheetToday.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
