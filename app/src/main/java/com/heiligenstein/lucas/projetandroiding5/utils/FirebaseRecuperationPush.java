package com.heiligenstein.lucas.projetandroiding5.utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.heiligenstein.lucas.projetandroiding5.Activity.LifiActivity;
import com.heiligenstein.lucas.projetandroiding5.R;

import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Lucas on 28/11/2018.
 */

// Pour récupérer les messages du Firebase Cloud Messaging
public class FirebaseRecuperationPush extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            // Pour afficher la popup de notification
            sendNotificationData(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }
    }

    // Envoyer une Push Notification au téléphone
    private void sendNotificationData(String messageTitle,String messageBody) {
        Intent intent = new Intent(this, LifiActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_lifi)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, mBuilder.build());
    }
}
