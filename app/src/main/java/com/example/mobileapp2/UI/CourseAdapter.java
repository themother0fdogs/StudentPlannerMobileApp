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
import java.util.zip.Inflater;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private CourseViewHolder(View itemview){
            super(itemview);
            courseItemView= itemview.findViewById(R.id.textView4);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position= getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context,CourseDetails.class);
                    intent.putExtra("Course ID: ", current.getCourseID());
                    intent.putExtra("Course Name: ", current.getCourseName());
                    intent.putExtra("Course Start: ", current.getCourseStart());
                    intent.putExtra("Course End: ", current.getCourseEnd());
                    intent.putExtra("Progress Status", current.getProgressStatus());
                    intent.putExtra("Instructor Name: ", current.getInstructorName());
                    intent.putExtra("Instructor Phone#: ", current.getInstructorPhone());
                    intent.putExtra("Instructor Email: ", current.getInstructorEmail());
                    intent.putExtra("Term Id: ", current.getTermID());
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
        View itemView= mInflater.inflate(R.layout.course_list_item, parent, false);
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
            holder.courseItemView.setText("No Information");
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
