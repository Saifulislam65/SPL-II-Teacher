package com.ban.teacher;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ObjectCourse {
    String marks;
    String assignTask;
    String attendanceSheet;

    public ObjectCourse() {
    }

    public ObjectCourse(String marks, String assignTask, String attendanceSheet) {
        this.marks = marks;
        this.assignTask = assignTask;
        this.attendanceSheet = attendanceSheet;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getAssignTask() {
        return assignTask;
    }

    public void setAssignTask(String assignTask) {
        this.assignTask = assignTask;
    }

    public String getAttendanceSheet() {
        return attendanceSheet;
    }

    public void setAttendanceSheet(String attendanceSheet) {
        this.attendanceSheet = attendanceSheet;
    }
}
