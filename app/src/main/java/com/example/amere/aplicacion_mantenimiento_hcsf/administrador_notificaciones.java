package com.example.amere.aplicacion_mantenimiento_hcsf;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

public class administrador_notificaciones extends ContextWrapper
{
    private NotificationManager manager;
    public static final String CHANNEL_HIGH_ID="1";
    private final String CHANNEL_HIGH_NAME="HIGH";
    public static final String CHANNEL_LOW_ID="2";
    private final String CHANNEL_LOW_NAME="LOW CHANNEL";
    private void crearCanales()
    {
        if(Build.VERSION.SDK_INT>=26)
        {
            NotificationChannel higChannel=new NotificationChannel(CHANNEL_HIGH_ID,CHANNEL_HIGH_NAME,NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel lowChannel=new NotificationChannel(CHANNEL_LOW_ID,CHANNEL_LOW_NAME,NotificationManager.IMPORTANCE_LOW);
            getManager().createNotificationChannel(higChannel);
            getManager().createNotificationChannel(lowChannel);
        }
    }

    public administrador_notificaciones(Context base) {
        super(base);
    }

    public NotificationManager getManager()
    {
        if(manager==null)
        {
            manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
    private Notification.Builder createNotification (String title, String message, boolean isHighImportance)
    {
        if(Build.VERSION.SDK_INT>=26)
        {
            if (isHighImportance)
            {
                return this.createNotificaionWithChannel(title,message,CHANNEL_HIGH_ID);
            }
            return this.createNotificaionWithChannel(title,message,CHANNEL_LOW_ID);
        }
        return this.createNotificaionWithoutChannel(title,message);
    }
    private Notification.Builder createNotificationWithChannel(String title, String message,String channelId)
    {
        if(Build.VERSION.SDK_INT>=26) {
            return new Notification.Builder(getApplicationContext(), channelId)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.icon_task)
                    .setAutoCancel(true);
        }
        return null;
    }
    private Notification.Builder createNotificaionWithoutChannel(String title, String message)
    {
        return new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.icon_task)
                .setAutoCancel(true);
    }
}