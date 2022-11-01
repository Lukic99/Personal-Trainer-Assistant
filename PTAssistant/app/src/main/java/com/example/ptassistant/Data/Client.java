package com.example.ptassistant.Data;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;
@Entity(tableName = "Client")
public class Client {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "age")
    private String age;

    @ColumnInfo(name = "sessions")
    private int numberOfTrainingSessions;

    @ColumnInfo(name = "log")
    private String trainingLog;

    public void setNumberOfTrainingSessions(int numberOfTrainingSessions) {
        this.numberOfTrainingSessions = numberOfTrainingSessions;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Client(String firstName, String lastName, String age) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        numberOfTrainingSessions=0;
//        LocalDate today = LocalDate.now();

//        trainingLog="Log:\n";
        trainingLog="Signed up: "+LocalDate.now().toString()+"\n";

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Client(){
    this.numberOfTrainingSessions = 0;
    this.trainingLog="Signed up: "+LocalDate.now().toString()+"\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTrainingLog(String trainingLog) {
        this.trainingLog = trainingLog;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public int getNumberOfTrainingSessions() {
        return numberOfTrainingSessions;
    }

    public String getTrainingLog() {
        return trainingLog;
    }

    public void addSession(){
        this.numberOfTrainingSessions++;
    }
    public void progressCheck(String text){
        trainingLog+=text+"\n";
    }

    @Override
    public String toString() {
        return lastName+" "+firstName+" ("+age+")";
    }
}
