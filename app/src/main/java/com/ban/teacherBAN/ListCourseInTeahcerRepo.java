package com.ban.teacherBAN;

public class ListCourseInTeahcerRepo {
    String courseId;

    public ListCourseInTeahcerRepo() {
    }

    public ListCourseInTeahcerRepo(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
