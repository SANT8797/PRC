package com.example.prc.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.prc.ChattingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;

import static android.os.Build.VERSION_CODES.O;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        SharedPreferences sp = getSharedPreferences("SP_USER",MODE_PRIVATE);
        String savedCurrentUser = sp.getString("Current_USERID","None");
        String sent = remoteMessage.getData().get("sent");
        String user = remoteMessage.getData().get("user");

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fUser != null ){
            if (!savedCurrentUser.equals(user)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    sendOreoNotification(remoteMessage);
                }
            }else{
            sendNotification(remoteMessage);}
        }
    }

    private void sendOreoNotification(RemoteMessage remoteMessage){
        String  user = remoteMessage.getData().get("user");
        String  icon = remoteMessage.getData().get("icon");
        String  title = remoteMessage.getData().get("title");
        String  body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int i = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, ChattingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("visit_user_id",user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,i,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        OreoNotification notification1 = new OreoNotification(this);
        Notification.Builder builder = notification1.getONotifications(title,body,pendingIntent,defSoundUri,icon);

        int j = 0 ;
        if (i>0){
            j=i;
        }
        notification1.getManager().notify(j,builder.build());
    }
    private void sendNotification(RemoteMessage remoteMessage) {

        String  user = remoteMessage.getData().get("user");
        String  icon = remoteMessage.getData().get("icon");
        String  title = remoteMessage.getData().get("title");
        String  body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int i = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, ChattingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("visit_user_id",user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,i,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentText(body)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(defSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int j = 0 ;
        if (i>0){
            j=i;
        }
        notificationManager.notify(j,builder.build());

    }
}
