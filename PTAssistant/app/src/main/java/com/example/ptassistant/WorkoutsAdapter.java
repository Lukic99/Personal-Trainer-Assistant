package com.example.ptassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptassistant.Data.AddClient;
import com.example.ptassistant.Data.Client;
import com.example.ptassistant.Data.DataBase;
import com.example.ptassistant.Data.MyDao;
import com.example.ptassistant.Data.Workout;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.MyViewHolder>{

    List<Workout> workouts;
    DataBase db;
    MyDao manager;
    Context con;
    Activity a;
    double d = 0;
    public WorkoutsAdapter(List<Workout> workouts, Context con, Activity a) {
        this.workouts = workouts;
        this.con = con;
        db = DataBase.getInstance(con);
        manager = db.manager();
        this.a = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_list_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Workout w = workouts.get(position);
        String tiime = w.getTime();
        Client c = manager.getClientById(w.getClient_id());
        String fullName = c.getFirstName()+" "+c.getLastName();
        holder.name.setText(fullName);
        holder.time.setText(tiime);
        holder.adress.setText(w.getAdress());
        holder.date.setText(w.getDate());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(con)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                               Workout w2 = manager.getWorkoutById(w.getId());
                                manager.deleteWorkout(w.getId());
                                workouts.remove(w);
//                                notifyDataSetChanged();
                                WorkoutsAdapter.this.notifyDataSetChanged();
                                String text="Workout removed";
                                Toast.makeText(con, text, Toast.LENGTH_LONG ).show();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return true;
            }
        });




        if(position == 0){
            LocProvider l = new LocProvider(con,a);
            new Thread(new Runnable() {
                @Override
                     public void run() {
                    do {
                        d = l.distanceToGym(w.getLat(), w.getLon());

                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                d = d/1000;
                                d = d*100; int e = (int) d;
                                d = e/100;
                                holder.distance.setText(d + "km");
                            }
                        });
//                        WorkoutsAdapter.this.notifyDataSetChanged();
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.out.println("Interrupted");
                        }
                    }while (1>0);
                }
            }).start();
            holder.distance.setText("Distance to gym: " + d + "m");
        }
//        holder.distance.setText(""+d);

    }

   public void removeWorkout(int id){

   }


    @Override
    public int getItemCount() {
        return workouts.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView time;
        public TextView name;
        public TextView date;
        public TextView adress;
        public TextView distance;
        public MyViewHolder(@NonNull View v) {
            super(v);
            this.time = v.findViewById(R.id.timeView);
            this.name = v.findViewById(R.id.nameView);
            this.date = v.findViewById(R.id.dateText);
            this.adress = v.findViewById(R.id.adressText);
            this.distance = v.findViewById(R.id.distance);
        }

    }

}
