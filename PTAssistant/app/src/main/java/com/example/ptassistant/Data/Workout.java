package com.example.ptassistant.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.time.format.DateTimeFormatter;

@Entity(tableName = "Workout", foreignKeys = @ForeignKey(entity = Client.class, parentColumns = "id", childColumns = "client_id"))
public class Workout implements Comparable<Workout>{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private int client_id;
    @ColumnInfo(name = "datee")
    private String date;
    @ColumnInfo(name = "timee")
    private String time;
    @ColumnInfo(name = "adress")
    private String adress;
    @ColumnInfo(name = "lattitude")
    private double lat;
    @ColumnInfo(name = "longitude")
    private double lon;
    @Ignore
    private DateTimeFormatter dtf;

    public Workout(int client, String date, String time, String adress, double lat, double lon) {
        this.client_id = client;
        this.date = date;
        this.time = time;
        this.adress = adress;
        this.lat = lat;
        this.lon = lon;
    }

    public Workout() {

    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getAdress() {
        return adress;
    }



    public int getId() {
        return id;
    }

    public int getClient_id() {
        return client_id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(Workout o) {
        return this.time.compareTo(o.getTime());
    }
}
