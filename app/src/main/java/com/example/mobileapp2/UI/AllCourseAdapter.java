package com.example.mobileapp2.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.R;

import java.util.ArrayList;

public class AllCourseAdapter extends RecyclerView.Adapter<AllCourseAdapter.CourseViewHolder> {
    private ArrayList<Course> courseArrayList;
    public AllCourseAdapter(ArrayList<Course> courseArrayList, Context context){
        this.courseArrayList = courseArrayList;
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemView;
        private final TextView startItemView;
        private final TextView endItemView;

        private CourseViewHolder(View itemview){
            super(itemview);
            courseItemView= itemview.findViewById(R.id.textViewcourselist);
            startItemView= itemview.findViewById(R.id.textViewstart);
            endItemView= itemview.findViewById(R.id.textViewend);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setClickable(false);
                }
            });
        }
    }

    public void searchResults (ArrayList<Course> results){
        courseArrayList = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public AllCourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_course_list_item, parent, false);
        return new CourseViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull AllCourseAdapter.CourseViewHolder holder, int position) {

        if (courseArrayList != null) {
                Course current = courseArrayList.get(position);
                String name = current.getCourseName();
                String start = current.getCourseStart();
                String end = current.getCourseEnd();

                holder.courseItemView.setText(name);
                holder.startItemView.setText(start);
                holder.endItemView.setText(end);
            }

             else {
                    String noInfo = "No Information";
                    holder.courseItemView.setText(noInfo);
                    holder.startItemView.setText(noInfo);
                    holder.endItemView.setText(noInfo);
                }
    }


    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }

}
