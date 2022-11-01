package com.example.ptassistant.viewPagerWorkouts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

public class AllWorkoutsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Workout> workouts;
    DataBase db;
    MyDao manager;
    Context c;
    FragmentManager fm;

    public AllWorkoutsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.all_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.allWorkoutsRecycler);
        workouts = new ArrayList<>();
//        c = getActivity().getApplicationContext();
        c=getContext();
        db = DataBase.getInstance(c);
        manager = db.manager();
        workouts = manager.getAllWorkouts();
        this.fm = getFragmentManager();

        WorkoutsAdapter wa = new WorkoutsAdapter(workouts,c,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(wa);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        workouts = manager.getAllWorkouts();

        WorkoutsAdapter wa = new WorkoutsAdapter(workouts,c,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(wa);
    }


}
