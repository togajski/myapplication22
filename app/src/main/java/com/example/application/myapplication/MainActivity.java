package com.example.application.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private Button button;
    private Button button3;
    public static final String CHANNEL_ID = "download";
    public static final String DOWNLOAD_ACTION = "download";
    Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
        Button createNotificationButton = findViewById(R.id.button3);


        createNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts the function below
                addNotification();
            }
        });
    }


    public void openActivity2() {

        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    // Creates and displays a notification
    private void addNotification() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH.mm");
        String currentDateandTime = sdf.format(new Date());
        Random r = new Random();
        int decimala = r.nextInt(59 - 31) + 31;
        Intent notIntent = new Intent(this, MainActivity.class);
        String htmlStr = "stringingirn";
        notIntent.putExtra("htmlStr", htmlStr);
        // Idemo staviti akciju da se vidi tko otvara aktivnost

        notIntent.setAction(DOWNLOAD_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setChannelId(MainActivity.CHANNEL_ID) // Od Android 8 svaka not. mora biti u kanalu
                .setContentTitle("Trenutni tečaj")
                .setContentText( currentDateandTime +  " srednji tečaj iznosi" + " 7." + decimala)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Gotov je notification, briše se
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(11, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}