package com.example.attendancesheet.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "attendance_data.db";
    private static final int VERSION_NUMBER = 1;

    private final static String ID = "id";
    private final static String COURSE_NAME = "course_name";
    private final static String COURSE_CODE = "course_code";

    //table for save course name
    final String COURSE = "course_name";
    final  String CODE = "course_code";
    final  String COURSE_TABLE_NAME = "course_name";
    final String CREATE_COURSE_TABLE="CREATE TABLE " + COURSE_TABLE_NAME + "( " + ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " + COURSE + " VARCHAR(100)," + CODE + " TEXT(6) NOT NULL)";




    public DBHelper(@Nullable Context context) {
        super( context, DATABASE_NAME, null, VERSION_NUMBER );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table "course"
        // column: id, name, code
        db.execSQL( CREATE_COURSE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean createCourse(CourseDetails courseDetails) {

        //write the course info in "course" table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( COURSE, courseDetails.getCoursename( ));
        contentValues.put( CODE, courseDetails.getCoursecode() );
        db.insert( COURSE_TABLE_NAME, null, contentValues );

        //final String TABLE_NAME = courseDetails.getCoursename()+courseDetails.getCoursecode();
        final String TABLE_NAME = courseDetails.coursename + courseDetails.coursecode;

        final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COURSE_NAME + " VARCHAR(100)," + COURSE_CODE + " TEXT(20) NOT NULL )";
        boolean tableCreated = false;
        try {
            //SQLiteDatabase db = getWritableDatabase();
            db.execSQL( CREATE_TABLE );
            db.close();
            tableCreated = true;
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tableCreated;
    }

//      public long insertData(CourseDetails courseDetails) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put( COURSE, courseDetails.getCoursename( ));
//        contentValues.put( CODE, courseDetails.getCoursecode() );
//
//        long rowId = db.insert( TABLE_NAME, null, contentValues );
//        return rowId;
//    }

//
//   void createCourseTable(CourseDetails courseDetails) {
//
//        //write the course info in "course" table
//
//       long result = db.insert( COURSE_TABLE_NAME, null, contentValues );
//
//if(result == -1){
//
//    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT  ).show();
//}else {
//    Toast.makeText(context,"Added Successful",Toast.LENGTH_SHORT  ).show();
//}
//    }



    public Cursor readAllData(){

        String query ="SELECT * FROM " + COURSE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =null;

        if(db!=null){
           cursor= db.rawQuery( query,null );
        }

        return  cursor;
    }


}
