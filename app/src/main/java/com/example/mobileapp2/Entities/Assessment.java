package com.example.mobileapp2.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="Assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentName;
    private String assessmentType;
    private String assessmentStart;
    private String assessmentEnd;

    private int courseID;

    int userID;


    public Assessment(int assessmentID, String assessmentName, String assessmentType, String assessmentStart, String assessmentEnd, int courseID, int userID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.courseID = courseID;
        this.userID = userID;
    }

    public Assessment() {
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentStart() {
        return assessmentStart;
    }

    public void setAssessmentStart(String assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public String getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(String assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString(){
        return assessmentType;
    }

}
