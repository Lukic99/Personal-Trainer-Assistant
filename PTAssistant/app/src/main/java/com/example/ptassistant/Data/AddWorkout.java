package com.example.ptassistant.Data;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ptassistant.GymLocActivity;
import com.example.ptassistant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddWorkout extends AppCompatActivity {

    private EditText title;
    private static TextView timeText;
    private static TextView dateText;
    private static TextView gymText;
    private Button dateButton;
    private Button timeButton;
    private Button gymButton;
    private Spinner clients;
    SharedPreferences sharedPref;
    FloatingActionButton done;
    FloatingActionButton back;
    DataBase db;
    MyDao manager;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workout);
        db = DataBase.getInstance(getApplicationContext());
        manager = db.manager();

        List<Client> clientList = manager.getAllClients();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPref.edit();
        ArrayAdapter<Client> clientAdapter = new ArrayAdapter<Client>(this, android.R.layout.simple_spinner_item, clientList);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clients = (Spinner) findViewById(R.id.clientsSpinner);
        clients.setAdapter(clientAdapter);

        title = (EditText) findViewById(R.id.schedule);
        title.setFocusable(false);
        timeText = (TextView) findViewById(R.id.timeText);
        dateText = (TextView) findViewById(R.id.dateText);
        gymText = (TextView) findViewById(R.id.gymText);
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        gymButton = (Button) findViewById(R.id.gymButton);
        done = (FloatingActionButton) findViewById(R.id.doneButton);
        back = (FloatingActionButton) findViewById(R.id.backButton);

        gymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWorkout.this, GymLocActivity.class);
                startActivity(intent);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adress = sharedPref.getString("Adress", "def");
                String date = dateText.getText().toString();
                String time = timeText.getText().toString();
                Client c = (Client) clients.getSelectedItem();
                int clientId = c.getId();
                double latitude = sharedPref.getFloat("latitude",0.0f);
                double longitude = sharedPref.getFloat("longitude",0.0f);
                if(adress.equals("") || time.equals("  At what time ") || date.equals("  Pick a date:  ")){
                    String text = "Enter all information";
                    Toast.makeText(AddWorkout.this, text, Toast.LENGTH_LONG).show();
                }else {
                    Workout w = new Workout(clientId, date, time, adress, latitude, longitude);
                    manager.insert(w);
                    System.out.println("Lat and Lon: "+latitude+" : "+longitude);
                    editor.putString("Adress","");
                    String text = "Workout saved :D";
                    Toast.makeText(AddWorkout.this, text, Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new AddWorkout.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddWorkout.TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String adresa = sharedPref.getString("Adress", "def");
        if(!adresa.equals("def")){
            gymText.setText(adresa);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
            System.out.println(df.format(c.getTime()));
            dateText.setText(df.format(c.getTime()));
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeText.setText(hourOfDay + ":" + minute);
        }
    }


}