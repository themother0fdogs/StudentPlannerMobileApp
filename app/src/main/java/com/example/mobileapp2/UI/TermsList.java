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

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Term;
import com.example.mobileapp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermsList extends AppCompatActivity {
    private Repository repository;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);

        userID = Integer.parseInt(getIntent().getExtras().get("userID").toString());
        repository = new Repository(getApplication());

        //terms recyclerview
        RecyclerView recyclerView = findViewById(R.id.termRecyclerview);
        repository = new Repository(getApplication());
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Term> matchingTerms = new ArrayList<>();
        for (Term term : repository.getAllTerms())
            if (term.getUserID() == userID) {
                matchingTerms.add(term);
            }
        termAdapter.setTerms(matchingTerms);

        //view courses button
        Button courseButton = findViewById(R.id.viewAllCourses);
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsList.this, CourseList.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }

        });
        //view assessments button
        Button assessmentButton = findViewById(R.id.viewAllAssessments);
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsList.this, AssessmentList.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        //add term fab
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsList.this, TermDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.termRecyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Term> refresh = new ArrayList<>();
        for (Term term : repository.getAllAssociatedTerms(userID))
            if (term.getUserID() == userID) {
                refresh.add(term);
            }
        termAdapter.setTerms(refresh);
    }

    //logout menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                Intent intent = new Intent(TermsList.this, UserLogin.class);
                startActivity(intent);
        }
        return true;
    }
}