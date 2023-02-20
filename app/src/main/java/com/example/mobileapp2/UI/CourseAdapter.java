package com.example.mobileapp2.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemview){
            super(itemview);
            courseItemView= itemview.findViewById(R.id.textViewcourselist);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        int position = getAdapterPosition();
                        final Course current = mCourses.get(position);
                        Intent intent = new Intent(context, CourseDetails.class);
                        intent.putExtra("courseID", current.getCourseID());
                        intent.putExtra("course", current.getCourseName());
                        intent.putExtra("start", current.getCourseStart());
                        intent.putExtra("end", current.getCourseEnd());
                        intent.putExtra("progress", current.getProgressStatus());
                        intent.putExtra("instructor", current.getInstructorName());
                        intent.putExtra("phone", current.getInstructorPhone());
                        intent.putExtra("email", current.getInstructorEmail());
                        intent.putExtra("termID", current.getTermID());
                        intent.putExtra("notes", current.getOptionalNotes());
                        intent.putExtra("userID", current.getUserID());
                        context.startActivity(intent);
                }
            });
        }

    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course current = mCourses.get(position);
            String name= current.getCourseName();
            holder.courseItemView.setText(name);
        }
        else{
            String noInfo = "No Course(s) Registered with Term";
            holder.courseItemView.setText(noInfo);
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
