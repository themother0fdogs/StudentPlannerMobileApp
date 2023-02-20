package com.example.mobileapp2.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobileapp2.Entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessments);

    @Update
    void update(Assessment assessments);

    @Delete
    void delete(Assessment assessments);

    @Query("SELECT * FROM ASSESSMENTS ORDER BY assessmentStart ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM ASSESSMENTS WHERE courseID= :courseID ORDER BY courseID ASC")
    List<Assessment> getAllAssociatedAssessments(int courseID);
}
