package com.ban.teacher;


public class AdapterCreateCourse {
    String a1_courseName, a2_courseCode, a3_departmentName;

    String a4_teacherID, a5_courseEnrollmentMode;

    public AdapterCreateCourse() {
    }

    public AdapterCreateCourse(String a1_courseName, String a2_courseCode, String a3_departmentName,
                               String a4_teacherID, String a5_courseEnrollmentMode) {
        this.a1_courseName = a1_courseName;
        this.a2_courseCode = a2_courseCode;
        this.a3_departmentName = a3_departmentName;
        this.a4_teacherID = a4_teacherID;
        this.a5_courseEnrollmentMode = a5_courseEnrollmentMode;
    }

    public String getA1_courseName() {
        return a1_courseName;
    }

    public void setA1_courseName(String a1_courseName) {
        this.a1_courseName = a1_courseName;
    }

    public String getA2_courseCode() {
        return a2_courseCode;
    }

    public void setA2_courseCode(String a2_courseCode) {
        this.a2_courseCode = a2_courseCode;
    }

    public String getA3_departmentName() {
        return a3_departmentName;
    }

    public void setA3_departmentName(String a3_departmentName) {
        this.a3_departmentName = a3_departmentName;
    }

    public String getA4_teacherID() {
        return a4_teacherID;
    }

    public void setA4_teacherID(String a4_teacherID) {
        this.a4_teacherID = a4_teacherID;
    }

    public String getA5_courseEnrollmentMode() {
        return a5_courseEnrollmentMode;
    }

    public void setA5_courseEnrollmentMode(String a5_courseEnrollmentMode) {
        this.a5_courseEnrollmentMode = a5_courseEnrollmentMode;
    }
}
