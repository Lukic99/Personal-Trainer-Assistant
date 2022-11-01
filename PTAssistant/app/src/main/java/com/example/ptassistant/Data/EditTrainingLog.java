package com.example.ptassistant.Data;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ptassistant.R;


public class EditTrainingLog extends AppCompatActivity {
    public TextView name;
    public EditText logView;
    private Button done;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_training_log);
        Bundle b = getIntent().getExtras();

        name = (TextView) findViewById(R.id.nameText);
        logView = (EditText) findViewById(R.id.logView);
        done = (Button) findViewById(R.id.confirmLog);

        String namee = b.getString("name");
        name.setText(namee);
        String log = b.getString("log");
        int id = Integer.parseInt(b.getString("id"));
        logView.setText(log);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBase db = DataBase.getInstance(getApplicationContext());
                MyDao manager = db.manager();
                Client c = manager.getClientById(id);
                c.setTrainingLog(logView.getText().toString());
                manager.update(c);

                String text = "Clients training log confirmed.";
                Toast.makeText(EditTrainingLog.this, text, Toast.LENGTH_LONG).show();
                finish();
            }
        });

//        manager.update();
//        Vratiti preko sharedPref log objekat pa u manageru ili gde vec pronaci objekat u listi i apdejtovati.
//        Zavsisi kako odlucis da radis sa bazom i klijentima,odnosno sa listama
    }

}
