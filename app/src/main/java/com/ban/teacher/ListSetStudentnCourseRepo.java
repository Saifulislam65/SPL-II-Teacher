package com.ban.teacher;

public class ListSetStudentnCourseRepo {
    String studentMail, marks, attendance, studentId,  uid;

    public ListSetStudentnCourseRepo() {
    }

    public ListSetStudentnCourseRepo(String studentMail,  String attendance, String uid) {
        this.studentMail = studentMail;
        this.attendance = attendance;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentMail() {
        return studentMail;
    }

    public void setStudentMail(String studentMail) {
        this.studentMail = studentMail;
    }


    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
