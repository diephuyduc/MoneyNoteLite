package com.example.myappv1.broadcast_receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.myappv1.MainActivity;
import com.example.myappv1.R;
import com.example.myappv1.View.HomeActivity;
import com.example.myappv1.fragment.LoanFragment;

import java.util.Date;

public class MyBroadcastReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(LoanFragment.MY_TITLE);
        PendingIntent pendingIntent;
        if(MainActivity.presentPassword==null||MainActivity.presentPassword.equals("")){
            pendingIntent=  PendingIntent.getActivity(context, 0, new Intent(context,MainActivity.class ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),0);
        }
        else {
            pendingIntent=  PendingIntent.getActivity(context, 0, new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),0);

        }
        Notification notification = new NotificationCompat.Builder(context, MyApplication.CHANNEL_ID )
                .setContentTitle("Hạn tất toán")
                .setContentText(title)
                .setSmallIcon(R.drawable.icon_add_64)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) new Date().getTime(), notification);

    }
}
