package com.example.dkucrut;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class detail_event extends AppCompatActivity {
    EditText editTextEvent;
    EditText editTextDate;
    EditText editTextDesc;
    Button btnSave;

    int id;
    CustomHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahEvent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void tambahEvent(){

        editTextEvent = (EditText) findViewById(R.id.eventFill);
        editTextDate  = (EditText) findViewById(R.id.dateFill);
        editTextDesc  = (EditText) findViewById(R.id.descFill);

        String event = editTextEvent.getText().toString();
        String tanggal = editTextDate.getText().toString();
        String desc = editTextDesc.getText().toString();

        Login login = new Login();
        String laporan = login.insertEvent(event, tanggal, desc);

        Toast.makeText(detail_event.this, "Event Created "+laporan, Toast.LENGTH_SHORT).show();
        /* restart acrtivity */
        finish();
        startActivity(getIntent());
        startActivity(new Intent(detail_event.this, AllKegiatan.class));
    }

}
