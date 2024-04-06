package com.example.btcollectionesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    
    private Button next, done;
    private EditText fName, age, weight, height, force;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        done = (Button) findViewById(R.id.btnDone);
        next = (Button) findViewById(R.id.btnNext);
        
        fName = (EditText) findViewById(R.id.editName);
        age = (EditText) findViewById(R.id.editAge);
        weight = (EditText) findViewById(R.id.editWeight);
        height = (EditText) findViewById(R.id.editHeight);
        force = (EditText) findViewById(R.id.editForce);

        Intent serviceIntent;
        serviceIntent = new Intent(this, SensorBackgroundService.class);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(serviceIntent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}