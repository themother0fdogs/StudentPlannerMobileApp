package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Assessment;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    TextView editCourseAssessment;
    EditText editAssessmentName;
    EditText editAssessmentStart;
    EditText editAssessmentEnd;

    //DatePicker
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    int assessmentID;
    String assessmentName;
    String start;
    String end;
    String assessmentType;
    int courseID;

    int userID;

    Assessment assessment;
    Repository repository;
    Assessment current;
    Course associatedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        editAssessmentName = findViewById(R.id.assessmentName);
        editAssessmentStart = findViewById(R.id.assessmentStart);
        editAssessmentEnd = findViewById(R.id.assessmentEnd);

        //DatePicker
        String formatter = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
        editAssessmentStart.setText(sdf.format(new Date()));
        editAssessmentEnd.setText(sdf.format(new Date()));

        assessmentName = getIntent().getStringExtra("assessmentName");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        assessmentType = getIntent().getStringExtra("assessmentType");
        assessmentID= getIntent().getIntExtra("assessmentID", -1);
        courseID = Integer.parseInt(getIntent().getExtras().get("courseID").toString());

        userID = Integer.parseInt(getIntent().getExtras().get("userID").toString());


        editAssessmentName.setText(assessmentName);
        editAssessmentStart.setText(start);
        editAssessmentEnd.setText(end);

        repository = new Repository(getApplication());

        editCourseAssessment = findViewById(R.id.courseAssessment);
        for(Course course : repository.getAllCourses()){
            if(course.getCourseID() == courseID){
                editCourseAssessment.setText(course.getCourseName());
            }
        }

        //type spinner
        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        String[] type ={"Objective", "Performance"};
        ArrayAdapter<String> assessmentArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,type);
        typeSpinner.setAdapter(assessmentArrayAdapter);
        typeSpinner.setSelection(assessmentArrayAdapter.getPosition(assessmentType));

        //save button
        Button button = findViewById(R.id.saveAssessment);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(assessmentID == -1){
                    assessment  = new Assessment(0, editAssessmentName.getText().toString(), typeSpinner.getSelectedItem().toString(), editAssessmentStart.getText().toString(), editAssessmentEnd.getText().toString(), courseID, userID);
                    repository.insert(assessment);
                }
                else{
                    assessment  = new Assessment(assessmentID, editAssessmentName.getText().toString(), typeSpinner.getSelectedItem().toString(), editAssessmentStart.getText().toString(), editAssessmentEnd.getText().toString(), courseID, userID);
                    repository.update(assessment);
                }
                finish();
            }
        });

        //DatePicker for start and end dates
        editAssessmentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editAssessmentStart.getText().toString();

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String dateString = formatter.format(currentDate);

                if (info.equals("")) info = dateString;
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, startDate,
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

        editAssessmentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editAssessmentEnd.getText().toString();

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String dateString = formatter.format(currentDate);

                if (info.equals("")) info = dateString;
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, endDate,
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
        editAssessmentStart.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd(){
        String formatter = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
        editAssessmentEnd.setText(sdf.format(myCalendarStart.getTime()));
    }

    //assessment menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editassessment, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.deleteassessment:
                for(Assessment assessment: repository.getAllAssessments()) {
                    if (assessment.getAssessmentID() == assessmentID){
                        current = assessment;}
                }
                for(Course course: repository.getAllCourses()){
                    if(course.getCourseID()==courseID){
                        associatedCourse= course;}
                }
                if(current.getAssessmentID()==assessmentID){
                    repository.delete(current);
                    Toast.makeText(AssessmentDetails.this, current.getAssessmentName() + " deleted from " + associatedCourse.getCourseName(), Toast.LENGTH_LONG).show();
                    finish();
                }
                return true;

            case R.id.notifystart:
                String startDateString = editAssessmentStart.getText().toString();
                String formatter = "MM/dd/yyyy";
                SimpleDateFormat sdfStart = new SimpleDateFormat(formatter, Locale.US);
                Date notifystart = null;
                try {
                    notifystart = sdfStart.parse(startDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long trigger = notifystart.getTime();
                Intent intentStart = new Intent(AssessmentDetails.this, MyReceiver.class);
                intentStart.putExtra("key", assessmentName + " assessment is starting on: " + startDateString);
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++UserLogin.numAlert, intentStart, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;

            case R.id.notifyend:
                String endDateString = editAssessmentEnd.getText().toString();
                String format = "MM/dd/yyyy";
                SimpleDateFormat sdfEnd = new SimpleDateFormat(format, Locale.US);
                Date notifyend = null;
                try {
                    notifyend = sdfEnd.parse(endDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long trigger2 = notifyend.getTime();
                Intent intentEnd = new Intent(AssessmentDetails.this, MyReceiver.class);
                intentEnd.putExtra("key", assessmentName + " assessment is ending on: " + endDateString);
                PendingIntent sender2 = PendingIntent.getBroadcast(AssessmentDetails.this, ++UserLogin.numAlert, intentEnd, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}