package com.example.ptassistant.Data;

import android.widget.RemoteViews;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Dao
public interface MyDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Client client);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Workout workout);

    @Update
    void update(Client client);

    @Update
    void update(Workout workout);

    @Delete
    void delete(Client client);

    @Delete
    void delete(Workout workout);

    @Query("SELECT * FROM Client C where C.id=:id ")
    Client getClientById(int id);

    @Query("SELECT * FROM Client")
    List<Client> getAllClients();

    @Query("SELECT * FROM Workout w where w.id=:id ")
    Workout getWorkoutById(int id);

    @Query("SELECT * FROM Workout order by datee")
    List<Workout> getAllWorkouts();

    @Query("SELECT * FROM Workout w where w.datee=:daate order by timee")
    List<Workout> getAllTodaysWorkouts(String daate);

    @Query("DELETE FROM Workout  where workout.id=:id")
    void deleteWorkout(int id);
}