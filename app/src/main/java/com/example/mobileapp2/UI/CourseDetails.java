package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CourseDetails extends AppCompatActivity {

    TextView saveWarning;

    EditText editCourseName;
    EditText editCourseStart;
    EditText editCourseEnd;
    EditText editInstructorName;
    EditText editPhone;
    EditText editEmail;
    EditText optionalNotes;

    //DatePicker
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    int courseID;
    String courseName;
    String start;
    String end;
    String progress;
    String instructor;
    String phone;
    String email;
    int termID;

    String notes;

    Course course;
    Repository repository;
    Course current;
    Term currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        editCourseName = findViewById(R.id.courseName);
        editCourseStart = findViewById(R.id.courseStart);
        editCourseEnd = findViewById(R.id.courseEnd);
        editInstructorName = findViewById(R.id.instructorName);
        editPhone = findViewById(R.id.instructorNumber);
        editEmail = findViewById(R.id.instructorEmail);
        optionalNotes = findViewById(R.id.notes);

        saveWarning = findViewById(R.id.mustSaveCourse);

        //DatePicker
        String formatter = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
        editCourseStart.setText(sdf.format(new Date()));
        editCourseEnd.setText(sdf.format(new Date()));

        courseName = getIntent().getStringExtra("course");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        progress = getIntent().getStringExtra("progress");
        instructor = getIntent().getStringExtra("instructor");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        courseID = getIntent().getIntExtra("courseID", -1);
        termID = Integer.parseInt(getIntent().getExtras().get("termID").toString());
        notes = getIntent().getStringExtra("notes");

        editCourseName.setText(courseName);
        editCourseStart.setText(start);
        editCourseEnd.setText(end);
        editInstructorName.setText(instructor);
        editPhone.setText(phone);
        editEmail.setText(email);
        optionalNotes.setText(notes);

        if(courseName==null && start==null && end==null && progress==null && instructor==null && phone==null && email==null){
            String saveWarningString = "MUST SAVE NEW COURSE BEFORE ADDING ASSESSMENT!";
            saveWarning.setText(saveWarningString);
        }
        else{
            String noChange = "";
            saveWarning.setText(noChange);
        }

        repository = new Repository(getApplication());

        //progress spinner
        Spinner progressSpinner=findViewById(R.id.progressSpinner);
        String[] prog ={"In-Progress", "Completed", "Dropped", "Plan to Take"};
        ArrayAdapter<String> courseArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, prog);
        progressSpinner.setAdapter(courseArrayAdapter);
        progressSpinner.setSelection(courseArrayAdapter.getPosition(progress));

        //recyclerview of assessments
        RecyclerView recyclerView=findViewById(R.id.matchingAssessments);
        repository= new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter= new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> matchingAssessments = new ArrayList<>();
        for(Assessment assessment: repository.getAllAssessments())
            if(assessment.getCourseID() == courseID){
                matchingAssessments.add(assessment);
            }
        assessmentAdapter.setAssessment(matchingAssessments);

        //save button
        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseID == -1) {
                    course = new Course(0, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), progressSpinner.getSelectedItem().toString(),
                            editInstructorName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString(), termID, optionalNotes.getText().toString());
                    repository.insert(course);
                } else {
                    course = new Course(courseID, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), progressSpinner.getSelectedItem().toString(),
                            editInstructorName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString(), termID, optionalNotes.getText().toString());
                    repository.update(course);
                }
                finish();
            }
        });

        //DatePicker for start and end dates
        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editCourseStart.getText().toString();

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String dateString = formatter.format(currentDate);

                if (info.equals("")) info = dateString;
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate,
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

        editCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editCourseEnd.getText().toString();

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String dateString = formatter.format(currentDate);

                if (info.equals("")) info = dateString;
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDate,
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
        editCourseStart.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd(){
        String formatter = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
        editCourseEnd.setText(sdf.format(myCalendarStart.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.matchingAssessments);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> refresh = new ArrayList<>();
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getCourseID() == courseID)
                refresh.add(assessment);
        }
        assessmentAdapter.setAssessment(refresh);
    }

//course menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editcourse, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.deletecourse:
                for(Course course: repository.getAllCourses()) {
                    if (course.getCourseID() == courseID){
                        current = course;}
                }
                for(Term term: repository.getAllTerms()){
                    if(term.getTermID()==termID){
                    currentTerm= term;}
                }
                if(current.getCourseID()==courseID){
                    repository.delete(current);
                    Toast.makeText(CourseDetails.this, current.getCourseName() + " deleted from " + currentTerm.getTermName(), Toast.LENGTH_LONG).show();
                    finish();
                }
                return true;

            case R.id.addassessment:
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
                return true;

            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, optionalNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Additional Notes");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.notifystart:
                String startDateString = editCourseStart.getText().toString();
                String formatter = "MM/dd/yyyy";
                SimpleDateFormat sdfStart = new SimpleDateFormat(formatter, Locale.US);
                Date notifystart = null;
                try {
                    notifystart = sdfStart.parse(startDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long trigger = notifystart.getTime();
                Intent intentStart = new Intent(CourseDetails.this, MyReceiver.class);
                intentStart.putExtra("key", courseName + " course is starting on: " + startDateString);
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intentStart, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;

            case R.id.notifyend:
                String endDateString = editCourseEnd.getText().toString();
                String format = "MM/dd/yyyy";
                SimpleDateFormat sdfEnd = new SimpleDateFormat(format, Locale.US);
                Date notifyend = null;
                try {
                    notifyend = sdfEnd.parse(endDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long trigger2 = notifyend.getTime();
                Intent intentEnd = new Intent(CourseDetails.this, MyReceiver.class);
                intentEnd.putExtra("key", courseName + " course is ending on: " + endDateString);
                PendingIntent sender2 = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intentEnd, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}