package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.R;

import java.util.ArrayList;


public class CourseList extends AppCompatActivity {
    private AllCourseAdapter courseAdapter;
    private ArrayList<Course> courseList;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        userID = Integer.parseInt(getIntent().getExtras().get("userID").toString());
        RecyclerView recyclerView = findViewById(R.id.allcourseRecyclerview);
        Repository repository = new Repository(getApplication());
        courseList = new ArrayList<>();
        for(Course course: repository.getAllCourses())
            if(course.getUserID() == userID){
                courseList.add(course);
            }
        courseAdapter = new AllCourseAdapter(courseList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(courseAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem search = menu.findItem(R.id.actionSearch);

        SearchView searchview = (SearchView) search.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String input) {
                results(input);
                return false;
            }
        });
        return true;
    }

    public void results (String text){
        ArrayList<Course> searchResults = new ArrayList<>();
        ArrayList<Course> empty = new ArrayList<>();
        for(Course item : courseList){
            if(item.getCourseName().toLowerCase().contains(text.toLowerCase())){
                searchResults.add(item);
            }
        }
        if(searchResults.isEmpty()){
            courseAdapter.searchResults(empty);
        }
        else{
            courseAdapter.searchResults(searchResults);
        }
    }
}
