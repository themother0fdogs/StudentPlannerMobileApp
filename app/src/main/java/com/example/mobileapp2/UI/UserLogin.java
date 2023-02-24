package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp2.Database.Repository;
import com.example.mobileapp2.Entities.Assessment;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.Entities.Term;
import com.example.mobileapp2.Entities.Users;
import com.example.mobileapp2.R;

public class UserLogin extends AppCompatActivity {
    public static int numAlert;
    private Repository repository;

    EditText inputUsername;
    EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //users
        Users user1=new Users (1, "test", "test");
        repository = new Repository(getApplication());
        repository.insert(user1);
        Users user2=new Users (2, "other", "other");
        repository = new Repository(getApplication());
        repository.insert(user2);

        //populated terms for user 1
        Term term1=new Term (1, "Spring 1", "02/17/2023", "02,28,2023",1);
        repository = new Repository(getApplication());
        repository.insert(term1);
        Term term2=new Term (2, "Summer 1", "06/01/2023", "07/01/2023", 1);
        repository = new Repository(getApplication());
        repository.insert(term2);

        //populated terms for user 2
        Term term3=new Term (3, "Term 1", "02/01/2023", "03/01/2023", 2);
        repository = new Repository(getApplication());
        repository.insert(term3);
        Term term4=new Term (4, "Term 2", "06/01/2023", "08/01/2023", 2);
        repository = new Repository(getApplication());
        repository.insert(term4);

        //populated courses for user 1
        Course course1 = new Course (1, "Software 1", "02/22/2023", "02/28/2023", "In-Progress", "Prof Linda", "789-555-7896", "test@email.com",1, " ", 1);
        repository = new Repository(getApplication());
        repository.insert(course1);
        Course course2 = new Course (2, "Math 1", "02/22/2023", "02/28/2023", "In-Progress", "Prof Bob", "123-555-1234", "bob@email.com",1, " ", 1);
        repository = new Repository(getApplication());
        repository.insert(course2);
        Course course3 = new Course (3, "Scripting and Programming", "02/22/2023", "02/28/2023", "Plan to Take", "Prof Gene", "555-555-5596", "gene@email.com",2, " ", 1);
        repository = new Repository(getApplication());
        repository.insert(course3);

        //populated courses for user 2
        Course course4 = new Course (4, "Science 1", "01/22/2023", "02/28/2023", "Completed", "Prof Louise", "222-222-2345", "louise@email.com",3, " ", 2);
        repository = new Repository(getApplication());
        repository.insert(course4);
        Course course5 = new Course (5, "Data Management 1", "02/22/2023", "02/28/2023", "In-Progress", "Prof Tina", "234-555-5678", "tina@email.com",3, " ", 2);
        repository = new Repository(getApplication());
        repository.insert(course5);
        Course course6 = new Course (6, "Software 1", "05/22/2023", "09/20/2023", "Plan to Take", "Prof Teddy", "555-235-5632", "teddy@email.com",4, " ", 2);
        repository = new Repository(getApplication());
        repository.insert(course6);

        //populated assessments for user 1
        Assessment assessment1=new Assessment (1, "Software Test", "Objective", "02/17/2023", "02/28/2023", 1, 1);
        repository = new Repository(getApplication());
        repository.insert(assessment1);
        Assessment assessment2=new Assessment (2, "Software Project", "Performance", "02/17/2023", "02/28/2023", 1, 1);
        repository = new Repository(getApplication());
        repository.insert(assessment2);
        Assessment assessment3=new Assessment (3, "Math Test", "Objective", "02/17/2023", "02/28/2023", 2, 1);
        repository = new Repository(getApplication());
        repository.insert(assessment3);

        //populated assessments for user 2
        Assessment assessment4=new Assessment (4, "Science Test", "Objective", "01/22/2023", "02/28/2023", 4, 2);
        repository = new Repository(getApplication());
        repository.insert(assessment4);
        Assessment assessment5=new Assessment (5, "Science Project", "Performance", "01/17/2023", "02/28/2023", 4, 2);
        repository = new Repository(getApplication());
        repository.insert(assessment5);
        Assessment assessment6=new Assessment (6, "Data Project", "Performance", "02/17/2023", "02/28/2023", 5, 2);
        repository = new Repository(getApplication());
        repository.insert(assessment6);

//end of populated data

        inputUsername =findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);

        repository = new Repository(getApplication());

        //login button
        Button loginButton=findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameString = inputUsername.getText().toString();
                String pwString = inputPassword.getText().toString();
                boolean valid = repository.validate(nameString, pwString);

                if (!valid) {
                    Toast.makeText(UserLogin.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                }
                    else {
                    for (Users users : repository.getAllUsers()) {
                        if (nameString.equals(users.getUsername())){
                            int userid = users.getUserID();
                        Intent intent = new Intent(UserLogin.this, TermsList.class);
                        intent.putExtra("userID", userid);
                        startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}