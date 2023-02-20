package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Assessment;
import com.example.mobileapp2.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentList extends AppCompatActivity {
    private Repository repository;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        userID = Integer.parseInt(getIntent().getExtras().get("userID").toString());

        //recyclerview of assessments
        RecyclerView recyclerView=findViewById(R.id.allAssessmentsRecyclerview);
        repository= new Repository(getApplication());
        final AllAssessmentAdapter assessmentAdapter= new AllAssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> refresh = new ArrayList<>();
        for(Assessment assessment: repository.getAllAssessments())
            if(assessment.getUserID() == userID){
                refresh.add(assessment);
            }
        assessmentAdapter.setAssessments(refresh);

    }
}