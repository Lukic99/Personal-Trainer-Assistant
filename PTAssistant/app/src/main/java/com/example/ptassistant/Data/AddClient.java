package com.example.ptassistant.Data;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ptassistant.AllClients;
import com.example.ptassistant.MainActivity;
import com.example.ptassistant.R;

public class AddClient extends AppCompatActivity {
    public EditText firstName;
    public EditText lastName;
    public EditText ageInput;
    public Button saveClient;
//    private Client client;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        saveClient = (Button) findViewById(R.id.saveClient);
        saveClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = (EditText)findViewById(R.id.firstNameInput);
                lastName = (EditText)findViewById(R.id.lastNameInput);
                ageInput = (EditText)findViewById(R.id.ageInput);

                String name = firstName.getEditableText().toString();
                String surname = lastName.getText().toString();
                String age = ageInput.getEditableText().toString();
                System.out.println("Name:"+name+".");
                if(name.equals("") || surname.equals("") || age.equals("")){
                    String text="You need to fill all inputs";
                    Toast.makeText(AddClient.this, text, Toast.LENGTH_LONG ).show();
                }else{
                Client c = new Client();
                c.setAge(age);
                c.setLastName(surname);
                c.setFirstName(name);

                DataBase db = DataBase.getInstance(getApplicationContext());
                MyDao manager = db.manager();
                manager.insert(c);
                firstName.setText("");
                lastName.setText("");
                ageInput.setText("");

                String text="New Client added! :D";
                Toast.makeText(AddClient.this, text, Toast.LENGTH_LONG ).show();

                Intent intent = new Intent(AddClient.this, AllClients.class);
                startActivity(intent);
                }
            }
        });

    }

}