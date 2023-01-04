package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.R;

public class CourseDetails extends AppCompatActivity {

    EditText editCourseName;
    EditText editCourseStart;
    EditText editCourseEnd;
    EditText editProgress;
    EditText editInstructorName;
    EditText editPhone;
    EditText editEmail;

    int courseID;
    String courseName;
    String start;
    String end;
    String progress;
    String instructor;
    String phone;
    String email;
    int termID;

    Course course;
    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);


        editCourseName=findViewById(R.id.courseName);
        editCourseStart=findViewById(R.id.courseStart);
        editCourseEnd=findViewById(R.id.courseEnd);
        editProgress=findViewById(R.id.courseProgress);
        editInstructorName=findViewById(R.id.instructorName);
        editPhone=findViewById(R.id.instructorNumber);
        editEmail=findViewById(R.id.instructorEmail);


        courseName = getIntent().getStringExtra("course");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        progress = getIntent().getStringExtra("progress");
        instructor = getIntent().getStringExtra("instructor");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        courseID= getIntent().getIntExtra("courseID", -1);
        termID=getIntent().getIntExtra("termID", -1);

        editCourseName.setText(courseName);
        editCourseStart.setText(start);
        editCourseEnd.setText(end);
        editProgress.setText(progress);
        editInstructorName.setText(instructor);
        editPhone.setText(phone);
        editEmail.setText(email);

        repository=new Repository(getApplication());

        //save button
        //TODO: button not working and is crashing the app

        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(courseID==-1){
                    course= new Course(0, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editProgress.getText().toString(),
                            editInstructorName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString(), 0);
                    repository.insert(course);
                }

                else{
                    course= new Course(courseID, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editProgress.getText().toString(),
                            editInstructorName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString(), termID);
                    repository.update(course);
                }
            }
        });


    }
}