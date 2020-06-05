package com.example.attendancesheet.DB;


public class CourseDetails  {

    String coursename,coursecode;
   private String id;

    public CourseDetails(String coursename, String coursecode) {
        this.coursename = coursename;
        this.coursecode = coursecode;
    }
    public CourseDetails(String id) {
        this.id = id;
    }


    public CourseDetails() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }


}
