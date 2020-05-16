package com.example.attendancesheet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesheet.DB.CourseDetails;
import com.example.attendancesheet.MainActivity;
import com.example.attendancesheet.R;
import com.example.attendancesheet.model.CourseEntity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private static ArrayList<CourseEntity> courseEntities;
    Context context;
    MainActivity mainActivity;


    public RecyclerViewAdapter(ArrayList<CourseEntity> courseEntities, MainActivity mainActivity) {
        this.courseEntities = courseEntities;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.course_sample_layout, parent, false );
        MyViewHolder myViewHolder = new MyViewHolder( view, mainActivity );
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CourseEntity courseEntity = courseEntities.get( holder.getAdapterPosition() );

        //for showing course name and coure code from Sqlite datbase
        holder.CoursetextView.setText( courseEntity.getCourseName() );
        holder.Codetextview.setText( courseEntity.getCourseCode() );

        holder.bind( courseEntity );
        holder.view.setBackgroundResource( R.color.White );
        if (MainActivity.is_in_action_mood) {
            if (MainActivity.selectionList.contains( courseEntities.get( position ) )) {
                holder.view.setBackgroundResource( R.color.grey_200 );
               // holder.mImageView.setImageResource(R.drawable.ic_check_circle_24dp);
            }
        }
    }

    @Override
    public int getItemCount() {
        return courseEntities.size();
    }

    public void removeData(ArrayList<CourseEntity> list) {
        for (CourseEntity courseEntity : list) {
            courseEntities.remove( courseEntity );
        }
        notifyDataSetChanged();
    }

    public void changeDataItem(int position, CourseEntity courseEntity) {
        courseEntities.set( position, courseEntity );
        notifyDataSetChanged();
    }

    // for edit
    public static ArrayList<CourseEntity> getDataSet() {
        return courseEntities;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        MainActivity mainActivity;
        TextView CoursetextView, Codetextview;
        CardView cardView;
        private View view;


        public MyViewHolder(@NonNull View itemView, MainActivity mainActivity) {
            super( itemView );
            view = itemView;
            this.mainActivity = mainActivity;
            CoursetextView = view.findViewById( R.id.tv_courseNameID );
            Codetextview = view.findViewById( R.id.tv_courseCodeID );
            cardView = view.findViewById( R.id.cardViewID );
            cardView.setOnLongClickListener( this );
            cardView.setOnClickListener( this );
        }

        public void bind(CourseEntity courseEntity) {
            CoursetextView.setText( courseEntity.getCourseName() );
            Codetextview.setText( String.valueOf( courseEntity.getCourseCode() ) );
        }

        @Override
        public void onClick(View v) {

            if (MainActivity.is_in_action_mood) {
                mainActivity.prepareSelection( getAdapterPosition() );
                notifyItemChanged( getAdapterPosition() );
            }

        }

        @Override
        public boolean onLongClick(View v) {
            mainActivity.prepareToolbar( getAdapterPosition() );
            return true;
        }
    }

    public void setCourse(ArrayList<CourseEntity> courseEntity) {
        courseEntities = courseEntity;
        notifyDataSetChanged();
    }

    public void addCourse(CourseEntity courseEntity) {
        courseEntities.add( courseEntity );
        notifyItemInserted( courseEntities.size() );

    }





}