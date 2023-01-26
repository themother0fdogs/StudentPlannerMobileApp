package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.Entities.Term;
import com.example.mobileapp2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    TextView saveWarning;

    EditText editTermName;
    EditText editTermStart;
    EditText editTermEnd;

    String termName;
    String termStart;
    String termEnd;
    int termID;
    Term term;
    Repository repository;

    Term current;
    int num;

    //DatePicker
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        editTermName=findViewById(R.id.termName);
        editTermStart=findViewById(R.id.termStart);
        editTermEnd=findViewById(R.id.termEnd);
        saveWarning=findViewById(R.id.mustSaveTerm);

        //DatePicker
        String formatter = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
        editTermStart.setText(sdf.format(new Date()));
        editTermEnd.setText(sdf.format(new  Date()));

        termName= getIntent().getStringExtra("termName");
        termStart= getIntent().getStringExtra("termStart");
        termEnd= getIntent().getStringExtra("termEnd");
        termID= getIntent().getIntExtra("termID", -1);

        editTermName.setText(termName);
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);

        if(termName==null && termStart==null && termEnd==null){
            String saveWarningString = "MUST SAVE NEW TERM BEFORE ADDING COURSE!";
            saveWarning.setText(saveWarningString);
        }
        else{
            String noChange = " ";
            saveWarning.setText(noChange);}

        repository = new Repository(getApplication());

        //recyclerview of courses
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
                finish();
            }
        });

        //DatePicker for start and end dates
        editTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editTermStart.getText().toString();

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String dateString = formatter.format(currentDate);

                if (info.equals("")) info = dateString;
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDate,
                        myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

            }});
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }};

        editTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editTermEnd.getText().toString();

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String dateString = formatter.format(currentDate);

                if (info.equals("")) info = dateString;
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDate,
                        myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }});
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }};
    }
    private void updateLabelStart(){
        String formatter = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
        editTermStart.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd(){
        String formatter = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
        editTermEnd.setText(sdf.format(myCalendarStart.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.matchingCourses);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> refresh = new ArrayList<>();
        for (Course course : repository.getAllCourses()) {
            if (course.getTermID() == termID)
                refresh.add(course);
        }
        courseAdapter.setCourses(refresh);
    }

    //term menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editterm, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteterm:
                for(Term term: repository.getAllTerms()) {
                    if (termID == term.getTermID() )
                        current = term;
                }
                num = 0;
                for(Course course: repository.getAllCourses()){
                    if(course.getTermID()==termID) ++num;
                }
                if(num ==0){
                    repository.delete(current);
                    Toast.makeText(TermDetails.this, current.getTermName() + " deleted.", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(TermDetails.this, "Unable to delete. Must delete all courses before deleting a term.", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.addcourse:
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("termID", termID);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}