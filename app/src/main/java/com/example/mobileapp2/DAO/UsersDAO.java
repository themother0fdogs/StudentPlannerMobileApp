package com.example.mobileapp2.DAO;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mobileapp2.Entities.Users;

import java.util.List;

@Dao
public interface UsersDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Users users);

    @Query("SELECT * FROM USERS ORDER BY userID ASC")
    List<Users> getAllUsers();

    @Query ("SELECT * FROM USERS WHERE username =:username AND password =:password")
    boolean validate(String username, String password);
}
