package com.example.btcollectionesting;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    
    private Button next, back, done, stop;
    private EditText fName, age, weight, height, force;
    private RelativeLayout firstScreen, secondScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        done = (Button) findViewById(R.id.btnDone);
        next = (Button) findViewById(R.id.btnNext);
        stop = (Button) findViewById(R.id.btnStop);
        back = (Button) findViewById(R.id.btnBack);
        
        fName = (EditText) findViewById(R.id.editName);
        age = (EditText) findViewById(R.id.editAge);
        weight = (EditText) findViewById(R.id.editWeight);
        height = (EditText) findViewById(R.id.editHeight);
        force = (EditText) findViewById(R.id.editForce);

        firstScreen = (RelativeLayout) findViewById(R.id.firstScreen);
        secondScreen = (RelativeLayout) findViewById(R.id.secondScreen);

        Intent serviceIntent;
        serviceIntent = new Intent(this, SensorBackgroundService.class);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Showing the back button so we can go back and do new user data
                next.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);

                // Getting strings from editTexts and sending them to the service by intent
                String s_name = fName.getText().toString();
                serviceIntent.putExtra("name", s_name);
                String s_age = age.getText().toString();
                serviceIntent.putExtra("age", s_age);
                String s_height = height.getText().toString();
                serviceIntent.putExtra("height", s_height);
                String s_weight = weight.getText().toString();
                serviceIntent.putExtra("weight", s_weight);

                // Passing to the second screen to enter the percentage of the force
                firstScreen.setVisibility(View.GONE);
                secondScreen.setVisibility(View.VISIBLE);

                // Enable the "Done" button so we can start the service
                done.setEnabled(true);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // sending data to the service by intent
                String s_force = force.getText().toString();
                serviceIntent.putExtra("force", s_force);

                // Disable the second screen so we get count down to start collecting data
                secondScreen.setVisibility(View.GONE);

                    /* Here some logic for the count down, then show the user that
                        we are collecting For better user experience */

                // After the count down we start the service and collect our data as needed
                /* startService(serviceIntent); */
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here we will stop collecting the data and go back to the first screen

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // This function can be used to check if the service is running.
    // Usage : isDataCollectionRunning(myService.class)
    public boolean isDataCollectionRunning(Class<?> serviceClass) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}