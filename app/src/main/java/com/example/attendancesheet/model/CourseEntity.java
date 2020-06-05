package com.example.attendancesheet.model;

public class CourseEntity {
    private int id;
    private  String courseName;
    private String courseCode;
    private String text;
    private boolean isSelected = false;

    public CourseEntity() {
    }

    public CourseEntity(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public CourseEntity(int id, String courseName, String courseCode) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
