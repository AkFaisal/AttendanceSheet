package com.example.attendancesheet.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.attendancesheet.model.CourseEntity;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    private  DBHelper db;
    CourseDetails courseDetails;
    Context context;
    private static final String DATABASE_NAME = "attendance_data.db";
    private static final int VERSION_NUMBER = 1;
    public final static String ID = "id";
    private final static String COURSE_NAME = "course_name";
    private final static String COURSE_CODE = "course_code";
    //table for save course name and code from each course table
    public static final String COURSE = "course_name";
    public static final String CODE = "course_code";
    private static final String COURSE_TABLE_NAME = "course_name";
    public static final String CREATE_COURSE_TABLE = "CREATE TABLE " + COURSE_TABLE_NAME + "( " + ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " + COURSE + " VARCHAR(100)," + CODE + " TEXT(6) NOT NULL)";

    public DBHelper(@Nullable Context context) {
        super( context, DATABASE_NAME, null, VERSION_NUMBER );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( CREATE_COURSE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        try {
//           deleteTable( TABLE_NAME );
//            onCreate( db );
//
//        } catch (Exception e) {
//
//        }
//
  }

    public int createCourse(CourseDetails courseDetails) {
        //write the course name,code in "course" table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //get table course name and course code  from individual table
        contentValues.put( COURSE, courseDetails.getCoursename() );
        contentValues.put( CODE, courseDetails.getCoursecode() );
        //insert course name,code from gating individual table
        long id = db.insert( COURSE_TABLE_NAME, null, contentValues );
        if (id > 0) {
            //create the table for each course
           final String TABLE_NAME = courseDetails.coursename + courseDetails.coursecode;
            //each course table create
            final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COURSE_NAME + " VARCHAR(100)," + COURSE_CODE + " TEXT(20) NOT NULL )";
            try {
                db.execSQL( CREATE_TABLE );
                db.close();
            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return (int) id;
    }

    // Read all courses from course_name table
    public ArrayList<CourseEntity> readCourses() {
        ArrayList<CourseEntity> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COURSE_TABLE_NAME;
        Cursor cursor = db.rawQuery( query, null );
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                int id = cursor.getInt( cursor.getColumnIndex( ID ) );
                String cName = cursor.getString( cursor.getColumnIndex( COURSE ) );
                String cCode = cursor.getString( cursor.getColumnIndex( CODE ) );
                courses.add( new CourseEntity( id, cName, cCode ) );
            }
        }
        //after completing the read operation close db instances
        // cursor.close();
        db.close();
        return courses;
    }
    //delete data from course_name table
    public void deleteData(int id) {
        //final String TABLE_NAME = courseDetails.coursename + courseDetails.coursecode;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( COURSE_TABLE_NAME, "id = ?", new String[]{String.valueOf( id )} );
        db.close();

    }
    //delete table from database
    public void deleteTable(String table ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String DROP_TABLE = "DROP TABLE IF EXISTS " + table ;
        db.execSQL( DROP_TABLE );
        db.close();

    }

}
