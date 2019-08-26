package com.daus.notificationexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationHelper notificationHelper = new NotificationHelper(this);
        notificationHelper.addSimpleNotification(1,
                R.mipmap.ic_launcher, "Judul Content", "Isi Pesan Dari Notification",
                MainActivity.class, 111, 0);
    }
}
