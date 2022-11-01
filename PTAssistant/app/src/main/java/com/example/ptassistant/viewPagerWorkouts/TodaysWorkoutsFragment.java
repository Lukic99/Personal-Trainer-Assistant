package com.example.ptassistant.viewPagerWorkouts;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptassistant.Data.Client;
import com.example.ptassistant.Data.DataBase;
import com.example.ptassistant.Data.MyDao;
import com.example.ptassistant.Data.Workout;
import com.example.ptassistant.R;
import com.example.ptassistant.WorkoutsAdapter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TodaysWorkoutsFragment extends Fragment {

    RecyclerView recyclerView;
    List<Workout> workouts;
    Context c;
    DataBase db;
    MyDao manager;
    String d;

    public TodaysWorkoutsFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.today_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.todayRecyclerView);

        c = getContext();
        db = DataBase.getInstance(c);
        manager = db.manager();

        LocalDate date = LocalDate.now();
        DateTimeFormatter df =  DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        d = date.format(df);
        workouts =manager.getAllTodaysWorkouts(d);
       // Collections.sort(workouts);
        WorkoutsAdapter wa = new WorkoutsAdapter(workouts,c,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(wa);
        return v;

    }



    @Override
    public void onResume() {
        super.onResume();
        workouts =manager.getAllTodaysWorkouts(d);

        WorkoutsAdapter wa = new WorkoutsAdapter(workouts,c,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(wa);
    }
}
