package com.example.mobileapp2.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp2.Database.Repository;
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

        Users user=new Users (1, "test", "test");
        repository = new Repository(getApplication());
        repository.insert(user);
        Users user2=new Users (2, "other", "other");
        repository = new Repository(getApplication());
        repository.insert(user2);

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
                        intent.putExtra("userID", userid); //maybe needs to be added?
                        startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}