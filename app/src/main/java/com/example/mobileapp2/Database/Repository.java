package com.example.mobileapp2.Database;

import android.app.Application;

import com.example.mobileapp2.DAO.AssessmentDAO;
import com.example.mobileapp2.DAO.CourseDAO;
import com.example.mobileapp2.DAO.TermDAO;
import com.example.mobileapp2.DAO.UsersDAO;
import com.example.mobileapp2.Entities.Assessment;
import com.example.mobileapp2.Entities.Course;
import com.example.mobileapp2.Entities.Term;
import com.example.mobileapp2.Entities.Users;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private AssessmentDAO mAssessmentDAO;
    private UsersDAO mUsersDAO;

    private List<Course> mAlLCourses;
    private List<Course>mAlLMatchingCourses;
    private List<Term> mAllTerms;
    private List<Term> mAllAssociatedTerms;
    private List<Assessment> mAllAssessments;
    private List<Assessment>mAlLMatchingAssessments;
    private List<Users> mAllUsers;
    private boolean mValidate;

    private static int NUMBER_OF_THREADS =4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public Repository(Application application) {
        AppDatabaseBuilder db = AppDatabaseBuilder.getDatabase(application);
        mCourseDAO=db.courseDAO();
        mTermDAO=db.termDAO();
        mAssessmentDAO=db.assessmentDAO();
        mUsersDAO=db.usersDAO();
    }

    //Users
    public List<Users> getAllUsers(){
        databaseExecutor.execute(()->{
            mAllUsers=mUsersDAO.getAllUsers();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllUsers;
    }

    public boolean validate(String username, String password) {
        databaseExecutor.execute(() -> {
            mValidate = mUsersDAO.validate(username, password);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mValidate;
    }

    public void insert(Users user){
        databaseExecutor.execute(()->{
            mUsersDAO.insert(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //Terms
    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Term> getAllAssociatedTerms(int userID){
        databaseExecutor.execute(()->{
            mAllAssociatedTerms=mTermDAO.getAllAssociatedTerms(userID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssociatedTerms;
    }

    //Courses
    public List<Course> getAllCourses(){
        databaseExecutor.execute(()->{
            mAlLCourses=mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAlLCourses;
    }

    public List<Course> getAllAssociatedCourses(int termID){
        databaseExecutor.execute(()->{
            mAlLMatchingCourses=mCourseDAO.getAllAssociatedCourses(termID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAlLMatchingCourses;
    }

    public List<Assessment> getAllAssociatedAssessments(int courseID){
        databaseExecutor.execute(()->{
            mAlLMatchingAssessments=mAssessmentDAO.getAllAssociatedAssessments(courseID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAlLMatchingAssessments;
    }

    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Assessments
    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessments=mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
