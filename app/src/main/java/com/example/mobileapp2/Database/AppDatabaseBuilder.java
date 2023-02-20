package com.example.mobileapp2.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mobileapp2.DAO.AssessmentDAO;
import com.example.mobileapp2.DAO.CourseDAO;
import com.example.mobileapp2.DAO.TermDAO;
import com.example.mobileapp2.DAO.UsersDAO;
import com.example.mobileapp2.Entities.Assessment;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.Entities.Term;
import com.example.mobileapp2.Entities.Users;

@Database(entities = {Course.class, Term.class, Assessment.class, Users.class}, version=1, exportSchema = false)
public abstract class AppDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract UsersDAO usersDAO();
    private static volatile AppDatabaseBuilder INSTANCE;
    static AppDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE ==null){
            synchronized (AppDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), AppDatabaseBuilder.class,"AppDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}