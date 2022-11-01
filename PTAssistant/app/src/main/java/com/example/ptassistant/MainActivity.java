package com.example.ptassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.ptassistant.Data.AddClient;
import com.example.ptassistant.Data.AddWorkout;
import com.example.ptassistant.Data.Workout;
import com.example.ptassistant.viewPagerWorkouts.AllWorkoutsFragment;
import com.example.ptassistant.viewPagerWorkouts.TodaysWorkoutsFragment;
import com.example.ptassistant.viewPagerWorkouts.ViewPagerAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton fab;
    private MenuItem clientList;
    private MenuItem addClient;

    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FusedLocationProviderClient location;
    FragmentManager fm;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location = LocationServices.getFusedLocationProviderClient(this);


//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fm = getSupportFragmentManager();

        adapter.addFragments(new TodaysWorkoutsFragment(),"TODAY");
        adapter.addFragments(new AllWorkoutsFragment(), "ALL APOINTMENTS");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        fab = (FloatingActionButton) findViewById(R.id.addNewWorkout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddWorkout.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addClient:
                Intent intent = new Intent(MainActivity.this, AddClient.class);
                startActivity(intent);
                return true;
            case R.id.clientList:
                Intent intent2 = new Intent(MainActivity.this,AllClients.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}