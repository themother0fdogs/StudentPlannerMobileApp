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

import java.util.List;


public class AllCourseAdapter extends RecyclerView.Adapter<AllCourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {

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
    private List<Course> mCourses;
    private final LayoutInflater mInflater;
    public AllCourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override

    public AllCourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.all_course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCourseAdapter.CourseViewHolder holder, int position) {

                if (mCourses != null) {

                    Course current = mCourses.get(position);
                    String name = current.getCourseName();
                    String start = current.getCourseStart();
                    String end = current.getCourseEnd();

                    holder.courseItemView.setText(name);
                    holder.startItemView.setText(start);
                    holder.endItemView.setText(end);


                } else {
                    String noInfo = "No Information";
                    holder.courseItemView.setText(noInfo);
                    holder.startItemView.setText(noInfo);
                    holder.endItemView.setText(noInfo);
                }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCourses (List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }

}
