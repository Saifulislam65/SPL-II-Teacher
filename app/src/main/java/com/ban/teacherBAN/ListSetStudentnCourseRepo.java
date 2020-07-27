package com.ban.teacherBAN;

public class ListSetStudentnCourseRepo {
    String studentMail, marks, attendance, studentId,  uid;

    public ListSetStudentnCourseRepo() {
    }

    public ListSetStudentnCourseRepo(String studentMail, String uid) {
        this.studentMail = studentMail;
        this.uid = uid;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getStudentMail() {
        return studentMail;
    }

    public void setStudentMail(String studentMail) {
        this.studentMail = studentMail;
    }


}
