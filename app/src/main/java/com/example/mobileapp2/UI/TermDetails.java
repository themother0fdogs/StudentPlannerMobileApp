package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.Entities.Term;
import com.example.mobileapp2.R;

import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {

    EditText editTermName;
    EditText editTermStart;
    EditText editTermEnd;

    String termName;
    String termStart;
    String termEnd;
    int termID;
    Term term;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        editTermName=findViewById(R.id.termName);
        editTermStart=findViewById(R.id.termStart);
        editTermEnd=findViewById(R.id.termEnd);

        termName= getIntent().getStringExtra("termName");
        termStart= getIntent().getStringExtra("termStart");
        termEnd= getIntent().getStringExtra("termEnd");
        termID= getIntent().getIntExtra("termID", -1);

        editTermName.setText(termName);
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);

        repository = new Repository(getApplication());

        //recycler view of courses
        RecyclerView recyclerView=findViewById(R.id.matchingCourses);
        repository= new Repository(getApplication());
        final CourseAdapter courseAdapter= new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Course> matchingCourse = new ArrayList<>();
        for(Course course: repository.getAllCourses())
            if(course.getTermID() == termID){
                matchingCourse.add(course);
            }
        courseAdapter.setCourses(matchingCourse);

        //save button
        Button button = findViewById(R.id.saveTerm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(termID==-1){
                    term= new Term(0, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
                    repository.insert(term);
                }
                else{
                    term= new Term(termID, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
                    repository.update(term);
                }

            }
        });

    }

    //menu item
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent(TermDetails.this, TermsList.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

}