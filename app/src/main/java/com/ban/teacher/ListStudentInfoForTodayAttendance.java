package com.ban.teacher;

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
}
