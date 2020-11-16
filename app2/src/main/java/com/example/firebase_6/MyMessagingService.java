package com.example.firebase_6;

import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());


    }

    public void showNotification(String title,String message){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"MyNotifications")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_foreground))
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(message);

        NotificationManagerCompat manager=NotificationManagerCompat.from(this);
        manager.notify(999,builder.build());

    }
}
