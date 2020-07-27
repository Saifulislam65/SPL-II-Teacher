package com.ban.teacherBAN;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAttendanceSheetToday extends RecyclerView.Adapter<AdapterAttendanceSheetToday.ViewHolder>{
    Context context;
    ArrayList<ListStudentInfoForTodayAttendance> attendance;


    public AdapterAttendanceSheetToday(Context context, ArrayList<ListStudentInfoForTodayAttendance> attendance){
        this.context = context;
        this.attendance = attendance;
    }

    @NonNull
    @Override
    public AdapterAttendanceSheetToday.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AdapterAttendanceSheetToday.ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_attendance_list_today_recyclerview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAttendanceSheetToday.ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(attendance.get(i).getStudentName());
        viewHolder.sid.setText(attendance.get(i).getStudentID());
        viewHolder.attendanceStatus.setText(attendance.get(i).getAttendanceStatus());
    }

    @Override
    public int getItemCount() {
        return attendance.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, attendanceStatus, sid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.adapter_attendance_list_Today_recyclerview_name);
            sid = itemView.findViewById(R.id.adapter_attendance_list_Today_recyclerview_id);
            attendanceStatus = itemView.findViewById(R.id.adapter_attendance_Today_list_recyclerview_status);
        }

    }
}
