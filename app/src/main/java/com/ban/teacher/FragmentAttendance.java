package com.ban.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class FragmentAttendance extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_fragment_attendance, container, false);

        CardView card_attendance_manual = view.findViewById(R.id.manual_attendance);
        CardView card_attendance_device = view.findViewById(R.id.device_attendance);
        CardView card_attendance_smart = view.findViewById(R.id.smart_attendance);

        card_attendance_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        card_attendance_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityDeviceAttendance.class);
                startActivity(intent);

            }
        });

        card_attendance_smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
