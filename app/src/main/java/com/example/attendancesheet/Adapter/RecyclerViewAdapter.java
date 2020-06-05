package com.example.attendancesheet.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesheet.DB.CourseDetails;
import com.example.attendancesheet.DB.DBHelper;
import com.example.attendancesheet.MainActivity;
import com.example.attendancesheet.R;
import com.example.attendancesheet.model.CourseEntity;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private static ArrayList<CourseEntity> mCourseEntities ;
    private static ArrayList<CourseDetails> mCourseDetails ;
    DBHelper db;
    private ItemClickListener mListener;


    public RecyclerViewAdapter(ArrayList<CourseEntity> mCourseEntities, ItemClickListener listener) {
        this.mCourseEntities = mCourseEntities;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.course_sample_layout, parent, false );
        MyViewHolder myViewHolder = new MyViewHolder( view );
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,  int position) {
       final CourseEntity courseEntity = mCourseEntities.get( holder.getAdapterPosition() );
        holder.CoursetextView.setText( courseEntity.getCourseName() );
        holder.Codetextview.setText( courseEntity.getCourseCode() );

        holder.bind( courseEntity );
        holder.view.setBackgroundResource( R.color.White );
        if (MainActivity.is_in_action_mood) {
            if (MainActivity.selectionList.contains( mCourseEntities.get( position ) )) {
                holder.view.setBackgroundResource( R.color.grey_200 );
               // holder.mImageView.setImageResource(R.drawable.ic_check_circle_24dp);


            }
        }
    }


    @Override
    public int getItemCount() {
        return mCourseEntities.size();
    }

    public void removeData(ArrayList<CourseEntity> list) {
        for (CourseEntity courseEntity : list) {

            mCourseEntities.remove( courseEntity );

        }
        notifyDataSetChanged();
    }

    public void changeDataItem(int position, CourseEntity courseEntity) {
        CourseEntity oldItem = mCourseEntities.get( position );
        courseEntity.setId( oldItem.getId() );
        mCourseEntities.set( position, courseEntity );
        notifyDataSetChanged();
    }

    // for edit
    public static ArrayList<CourseEntity> getDataSet() {
        return mCourseEntities;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView CoursetextView, Codetextview;
        CardView cardView;
        private View view;

  ImageView delete; //............................................................................


        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            view = itemView;
            CoursetextView = view.findViewById( R.id.tv_courseNameID );
            Codetextview = view.findViewById( R.id.tv_courseCodeID );
            cardView = view.findViewById( R.id.cardViewID );cardView.setOnLongClickListener( this );
            cardView.setOnClickListener( this );
            //............................................................................................
           // delete = (ImageView) itemView.findViewById(R.id.menu_delete_Button_ID);
           // delete.setOnClickListener( this );



        }

        public void bind(CourseEntity courseEntity) {
            CoursetextView.setText( courseEntity.getCourseName() );
            Codetextview.setText( String.valueOf( courseEntity.getCourseCode() ) );
        }

        @Override
        public void onClick(View v) {
            if(mListener != null) {
                int position = getAdapterPosition();
                mListener.onClick( mCourseEntities.get( position ),  position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(mListener != null) {
                int position = getAdapterPosition();
                mListener.onLongClick( mCourseEntities.get( position ),  position);
            }
            return true;
        }
    }

    public void setCourse(ArrayList<CourseEntity> courseEntity) {
        mCourseEntities = courseEntity;
        notifyDataSetChanged();
    }

    public void addCourse(CourseEntity courseEntity) {
        mCourseEntities.add( courseEntity );
        notifyItemInserted( mCourseEntities.size() );

    }



    public interface ItemClickListener {
        void onClick(CourseEntity course, int adapterPosition);
        void onLongClick(CourseEntity course, int adapterPosition);
    }


}