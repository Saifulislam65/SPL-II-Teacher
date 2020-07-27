package com.ban.teacherBAN;

import java.util.Comparator;

public class ListStudentInfoForTodayAttendance {
    String studentName, studentID, email, attendanceStatus;

    public ListStudentInfoForTodayAttendance() {
    }

    public ListStudentInfoForTodayAttendance(String email, String studentID, String studentName) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.email = email;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Comparator<ListStudentInfoForTodayAttendance> sortByStudentID = new Comparator<ListStudentInfoForTodayAttendance>() {

        public int compare(ListStudentInfoForTodayAttendance s1, ListStudentInfoForTodayAttendance s2) {

            int rollno1 = Integer.parseInt(s1.getStudentID());
            int rollno2 = Integer.parseInt(s2.getStudentID());

            /*For ascending order*/
            return rollno1-rollno2;

            /*For descending order*/
            //rollno2-rollno1;
        }};
}
