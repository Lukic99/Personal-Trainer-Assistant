package com.example.ptassistant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.ptassistant.Data.AddClient;
import com.example.ptassistant.Data.Client;
import com.example.ptassistant.Data.DataBase;
import com.example.ptassistant.Data.MyDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AllClients extends AppCompatActivity {

    RecyclerView recyclerView;
    AllClientsAdapter aca;
    FloatingActionButton addClient;
    DataBase db;
    MyDao manager;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clients);

        recyclerView = (RecyclerView) findViewById(R.id.clientRecyclerView);
        addClient = (FloatingActionButton) findViewById(R.id.addNewCliient);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        db = DataBase.getInstance(getApplicationContext());
        manager = db.manager();

        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllClients.this, AddClient.class);
                startActivity(intent);
            }
        });

        List<Client> clients = manager.getAllClients();

        aca= new AllClientsAdapter(clients,AllClients.this);
        recyclerView.setAdapter(aca);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Client> clients = manager.getAllClients();
        aca= new AllClientsAdapter(clients,AllClients.this);
        recyclerView.setAdapter(aca);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent2 = new Intent(AllClients.this,MainActivity.class);
        startActivity(intent2);
    }
}