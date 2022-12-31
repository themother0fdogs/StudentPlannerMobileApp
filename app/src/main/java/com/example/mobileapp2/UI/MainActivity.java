package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.Entities.Term;
import com.example.mobileapp2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);

        //insert term
        /*Term term=new Term(0, "Term 2", "01/01/2023", "05/05/2023");
        Repository repository = new Repository(getApplication());
        repository.insert(term);*/



       /*Course course=new Course(0, "name", "start", "end", "progress", "instructor", "phone", "email", 1);
        Repository repository = new Repository(getApplication());
        repository.insert(course);*/


        //on click edit button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, CourseList.class);
                startActivity(intent);
            }
        });

    }

}