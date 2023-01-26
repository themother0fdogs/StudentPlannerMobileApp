package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Term;
import com.example.mobileapp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermsList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);

       //view courses button
        Button courseButton = findViewById(R.id.viewAllCourses);
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsList.this, CourseList.class);
                startActivity(intent);

            }

        });
        //view assessments button
        Button assessmentButton = findViewById(R.id.viewAllAssessments);
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsList.this, AssessmentList.class);
                startActivity(intent);
            }
        });

        //add term fab
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(TermsList.this, TermDetails.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView=findViewById(R.id.termRecyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository= new Repository(getApplication());
        List<Term> allTerms=repository.getAllTerms();
        termAdapter.setTerms(allTerms);
    }

}