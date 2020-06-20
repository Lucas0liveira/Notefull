package com.example.notefull;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Lembrete extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle b = intent.getExtras();
        final long userId = b.getLong("userId");
        final long id = b.getLong("id");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify")
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle("Notefull")
                .setContentText("Você possui um lembrete para agora")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Intent myIntent = new Intent(context, NoteActivity.class);
       notificationManager.notify(200, builder.build());
    }
}
