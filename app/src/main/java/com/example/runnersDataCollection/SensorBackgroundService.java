package com.example.runnersDataCollection;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SensorBackgroundService extends Service implements SensorEventListener {
    private static final String TAG = "SensorService";
    private static final int NOTIFICATION_ID = 12345;
    private static final String CHANNEL_ID = "SensorServiceChannel";

    private SensorManager sensorManager;
    private Sensor sensor;
    private FileWriter writer;
    private PowerManager.WakeLock wakeLock;
    private Intent mIntent;


    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SensorBackgroundService:WakeLock");
        wakeLock.acquire();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mIntent = intent; // Storing the intent received in onStartCommand

        //Testing extra data if received

        if (intent != null) {
            String extraData = intent.getStringExtra("name");
            if (extraData != null) {
                // Do something with the extra data
                Log.d(TAG, "Received extra data: " + extraData);
            }
        }



        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.e(TAG, "Accelerometer sensor not available.");
        }

        // Create and show a notification for the foreground service
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Write sensor data to CSV file
        writeCsv(event, mIntent);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Create a notification for the foreground service
    private Notification createNotification() {
        // Create a notification channel for Android Oreo and above
        createNotificationChannel();

        // Create an intent to launch your app's main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Sensor Service")
                .setContentText("Running")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent); // Set the PendingIntent to launch the app's main activity

        return builder.build();
    }

    // Create a notification channel for Android Oreo and above
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sensor Service Channel";
            String description = "Channel for Sensor Service";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void  writeCsv(SensorEvent event, Intent intent){

        // Getting data from intent's extras
        String name = intent.getStringExtra("name");
        int age = Integer.valueOf(intent.getStringExtra("age"));
        int height = Integer.valueOf(intent.getStringExtra("height"));
        int weight = Integer.valueOf(intent.getStringExtra("weight"));
        String force = intent.getStringExtra("force");

        String file_name = name+String.valueOf((height*weight)/10);

        try {
            if (writer == null) {
                File file = new File(getExternalFilesDir(null), file_name+".csv");
                writer = new FileWriter(file, true); // append mode
            }
            String data = name + "," + event.timestamp + "," + event.values[0] + "," + event.values[1]
                    + "," + event.values[2] + "," + height + "," + weight + "," + age + "," + force + "\n";
            writer.write(data);

            // Debugging ..
            Log.d(TAG, "SensorData: " + data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
