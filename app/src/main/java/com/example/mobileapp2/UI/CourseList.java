package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.R;

import java.util.List;

public class CourseList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        //recyclerview
        RecyclerView recyclerView=findViewById(R.id.courseRecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository= new Repository(getApplication());
        List<Course> allCourses=repository.getAllCourses();


    }
}