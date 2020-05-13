package com.example.attendancesheet;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.attendancesheet.Adapter.RecyclerViewAdapter;
import com.example.attendancesheet.DB.CourseDetails;
import com.example.attendancesheet.DB.DBHelper;
import com.example.attendancesheet.databinding.ActivityMainBinding;
import com.example.attendancesheet.databinding.AddCourseSampleLayoutBinding;
import com.example.attendancesheet.databinding.EditCourseSampleLayoutBinding;
import com.example.attendancesheet.model.CourseEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // action mode
    public static boolean is_in_action_mood = false;
    public static ArrayList<CourseEntity> selectionList = new ArrayList<>();


    private ArrayList<CourseEntity> mCourseEntitie = new ArrayList<>();
    int counter = 0;

    MainActivity mainActivity;
    RecyclerViewAdapter.MyViewHolder myViewHolder;

    private ActivityMainBinding mbinding;
    private RecyclerViewAdapter recyclerViewAdapter;
    private CourseEntity mCourseEntity;

    //RecyclerView recyclerView;
    Context context;
    Toolbar toolbar;

    //Database
    DBHelper dbHelper;
    CourseDetails courseDetails;
   // CourseTable courseTable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        dbHelper = new DBHelper( MainActivity.this );
        mbinding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        toolbar = findViewById( R.id.toolbarID );
        setSupportActionBar( mbinding.include.toolbarID ); //for add custom Tool bar......
        // set recyclerview
        mbinding.rvAdapterID.setLayoutManager( new GridLayoutManager( this, 2 ) );
        // adapter
        recyclerViewAdapter = new RecyclerViewAdapter( mCourseEntitie, this );
        mbinding.rvAdapterID.setAdapter( recyclerViewAdapter );

        mbinding.rvAdapterID.setHasFixedSize( true ); // performance improve for recyclerview
        mbinding.include.toobarSelectdedItemCunterID.setVisibility( View.GONE );//SelectdedItemCunter visibility

recyclerViewAdapter.setCourse( mCourseEntitie );


    }

    void displayData(){
        Cursor cursor =dbHelper.readAllData();
        if(cursor.getCount() == 0){

            Toast.makeText(context,"No Data",Toast.LENGTH_SHORT  ).show();

        }else {

            while (cursor.moveToNext()){
                cursor.getString( 1 );
                cursor.getString( 2 );




            }


        }


    }


    //for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.custome_main_menu_layout, menu );
        return super.onCreateOptionsMenu( menu );
    }
    //for selected item + menu action mood
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addCourse_menu_ID) {
            addCourse();
        }
        if (item.getItemId() == R.id.menu_edit_Button_ID) {

            if (selectionList.size() == 1) {
                LayoutInflater inflater = LayoutInflater.from( this );
                final EditCourseSampleLayoutBinding ebinding = DataBindingUtil.inflate(
                        inflater, R.layout.edit_course_sample_layout, null, false
                );

                final AlertDialog dialog = new AlertDialog.Builder( this )

                        .setView( ebinding.getRoot() )
                        .setTitle( R.string.editcourse )
                        .create();

                ebinding.editbuttonSaveID.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String course_name = ebinding.editTextCourseTitleID.getText().toString();
                        String course_code = ebinding.editTextCourseCodeID.getText().toString();

                        if (TextUtils.isEmpty( course_name )) {
                            Toast.makeText( MainActivity.this, "Please enter the course name", Toast.LENGTH_SHORT ).show();
                            return;
                        }

                        if (TextUtils.isEmpty( course_code ) || !TextUtils.isDigitsOnly( course_code )) {
                            Toast.makeText( MainActivity.this, "Please enter valid course code", Toast.LENGTH_SHORT ).show();
                            return;
                        }
                        dialog.dismiss();
                        Toast.makeText( MainActivity.this, "Edit successful", Toast.LENGTH_SHORT ).show();
                        CourseEntity courseEntity = new CourseEntity( course_name, course_code );
                        recyclerViewAdapter.changeDataItem( getCheckedLastPosition(), courseEntity );
                        clearActionMode();
                        mbinding.rvAdapterID.smoothScrollToPosition( recyclerViewAdapter.getItemCount() );

                    }
                } );

                dialog.show();

            }

        } else if (item.getItemId() == R.id.menu_delete_Button_ID) {

            AlertDialog.Builder builder=new AlertDialog.Builder( MainActivity.this );
            builder.setMessage("Are you sure!" ).setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    is_in_action_mood = false;
                    recyclerViewAdapter.removeData( selectionList );
                    clearActionMode();
                    Toast.makeText( MainActivity.this, "Deleted", Toast.LENGTH_SHORT ).show();
                }
            } ).setNegativeButton( "Cancel",null);

            AlertDialog alert =builder.create();
            alert.show();
        } else if (item.getItemId() == android.R.id.home) {
            clearActionMode();
            recyclerViewAdapter.notifyDataSetChanged();
        }

        return true;

    }

    // foe create course from menu
    private void addCourse() {


        LayoutInflater inflater = LayoutInflater.from( this );
        final AddCourseSampleLayoutBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.add_course_sample_layout, null, false
        );

        final AlertDialog dialog = new AlertDialog.Builder( this )

                .setView( binding.getRoot() )
                .setTitle( R.string.addcourse )
                .create();

        binding.addButtonID.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String course_name = binding.addEdtCTID.getText().toString();
                String course_code = binding.addEdtCCID.getText().toString();

                courseDetails =new CourseDetails(  );
                courseDetails.setCoursename(course_name);
                courseDetails.setCoursecode(course_code);

                if (TextUtils.isEmpty( course_name )) {
                    Toast.makeText( MainActivity.this, "Please enter the Cname", Toast.LENGTH_SHORT ).show();
                    return;

                }

                if (TextUtils.isEmpty( course_code ) || !TextUtils.isDigitsOnly( course_code )) {
                    Toast.makeText( MainActivity.this, "Please enter valid Ccode", Toast.LENGTH_SHORT ).show();
                    return;
                }
                //dbHelper.insertData(courseDetails);
               dbHelper.createCourse( courseDetails );
                dialog.dismiss();






                CourseEntity courseEntity = new CourseEntity( course_name, course_code );
                recyclerViewAdapter.addCourse( courseEntity );
                mbinding.rvAdapterID.smoothScrollToPosition( recyclerViewAdapter.getItemCount() );


            }
        } );

        dialog.show();
    }
   //selection item counter
    public void prepareSelection(int position) {
        if (!selectionList.contains( mCourseEntitie.get( position ) )) {
            selectionList.add( mCourseEntitie.get( position ) );
        } else {
            selectionList.remove( mCourseEntitie.get( position ) );
        }

        updateViewCounter();
    }

    private void updateViewCounter() {
        int counter = selectionList.size();
        if (counter == 1) {
            // edit
            mbinding.include.toolbarID.getMenu().getItem( 0 ).setVisible( true );
        } else {
            mbinding.include.toolbarID.getMenu().getItem( 0 ).setVisible( false );
        }

        mbinding.include.toolbarID.setTitle( counter + " item(s) selected" );
    }

    public void prepareToolbar(int position) {

        mbinding.include.toolbarID.getMenu().clear();
        mbinding.include.toolbarID.inflateMenu( R.menu.custom_menu_action_mood_layout );
        is_in_action_mood = true;
        recyclerViewAdapter.notifyDataSetChanged();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }

        prepareSelection( position );
    }

    public void clearActionMode() {
        is_in_action_mood = false;
        mbinding.include.toolbarID.getMenu().clear();
        mbinding.include.toolbarID.inflateMenu( R.menu.custome_main_menu_layout );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( false );
        }
        mbinding.include.toolbarID.setTitle( R.string.app_name );
        selectionList.clear();
    }

    //for normal mood when click back  mood from action menu
    @Override
    public void onBackPressed() {
        if (is_in_action_mood) {
            clearActionMode();
            recyclerViewAdapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
    }

    public int getCheckedLastPosition() {
        ArrayList<CourseEntity> dataSet = RecyclerViewAdapter.getDataSet();
        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get( i ).equals( selectionList.get( 0 ) )) {
                return i;
            }
        }
        return 0;
    }

}
