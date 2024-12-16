package com.dirajarasyad.carthub.service;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dirajarasyad.carthub.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseMessageReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.d("FCM", "Notification Received");

        if (message.getNotification() != null) {
            String title = message.getNotification().getTitle();
            String content = message.getNotification().getBody();

            Log.d("FCM", "Content Available");

            if (!message.getData().isEmpty()) {
                Map<String, String> data = message.getData();
                handleDataPayload(title, content, data);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                NotificationService notificationService = new NotificationService(this);
                notificationService.sendNotification("firebase_notification", title, content, intent);
            }
        }
    }

    private void handleDataPayload(String title, String content, Map<String, String> data) {
        String actionType = data.get("action_type");
        Intent intent;

        switch (actionType) {
            default:
                intent = new Intent(this, MainActivity.class);
                break;
        }

        for (Map.Entry<String, String> entry: data.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }

        NotificationService notificationService = new NotificationService(this);
        notificationService.sendNotification("firebase_notification" ,title, content, intent);
    }
}
