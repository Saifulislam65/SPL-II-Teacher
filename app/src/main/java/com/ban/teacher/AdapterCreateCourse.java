package com.ban.teacher;


public class AdapterCreateCourse {
    String a1_courseName, a2_courseCode, a3_departmentName;

    String a4_teacherID, a5_studentList, assignTask, attendanceSheet, marks, resource;

    public AdapterCreateCourse() {
    }

    public AdapterCreateCourse(String a1_courseName, String a2_courseCode, String a3_departmentName,
                               String a4_teacherID, String a5_studentList, String assignTask,
                               String attendanceSheet, String marks, String resource) {
        this.a1_courseName = a1_courseName;
        this.a2_courseCode = a2_courseCode;
        this.a3_departmentName = a3_departmentName;
        this.a4_teacherID = a4_teacherID;
        this.a5_studentList = a5_studentList;
        this.assignTask = assignTask;
        this.attendanceSheet = attendanceSheet;
        this.marks = marks;
        this.resource = resource;
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

    public String getA5_studentList() {
        return a5_studentList;
    }

    public void setA5_studentList(String a5_studentList) {
        this.a5_studentList = a5_studentList;
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

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
