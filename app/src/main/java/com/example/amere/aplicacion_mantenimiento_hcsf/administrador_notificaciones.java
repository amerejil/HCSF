package com.example.amere.aplicacion_mantenimiento_hcsf;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.example.amere.aplicacion_mantenimiento_hcsf.Activities.Detalle_tareas;
import com.example.amere.aplicacion_mantenimiento_hcsf.Activities.Detalle_tareas_finalizadas;

public class administrador_notificaciones extends ContextWrapper {
    public static final String CHANNEL_HIGH_ID = "1";
    public static final String CHANNEL_LOW_ID = "2";
    private final String CHANNEL_HIGH_NAME = "HIGH";
    private final String CHANNEL_LOW_NAME = "LOW CHANNEL";
    private NotificationManager manager;
    private Intent intent = null;

    public administrador_notificaciones(Context base) {
        super(base);
        crearCanales();
    }

    private void crearCanales() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel higChannel = new NotificationChannel(CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH);
            higChannel.enableLights(true);
            higChannel.setLightColor(Color.YELLOW);
            NotificationChannel lowChannel = new NotificationChannel(CHANNEL_LOW_ID, CHANNEL_LOW_NAME, NotificationManager.IMPORTANCE_LOW);
            getManager().createNotificationChannel(higChannel);
            getManager().createNotificationChannel(lowChannel);
        }
    }

    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public Notification.Builder createNotification(String title, String message, boolean isHighImportance, data_task data) {
        if (Build.VERSION.SDK_INT >= 26) {
            if (isHighImportance) {
                return this.createNotificationWithChannel(title, message, CHANNEL_HIGH_ID, data);
            }
            return this.createNotificationWithChannel(title, message, CHANNEL_LOW_ID, data);
        }
        return this.createNotificationWithoutChannel(title, message, data);
    }

    private Notification.Builder createNotificationWithChannel(String title, String message, String channelId, data_task data) {
        if (Build.VERSION.SDK_INT >= 26) {
            String estado = data.getEstado();
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            //Intent intent=new Intent(this,Detalle_tareas.class);
            if (!estado.equals("Finalizado")) {
                intent = new Intent(this, Detalle_tareas.class);
                intent.putExtra("data", data);
                intent.putExtra("trabajos", "diarios");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            } else {
                intent = new Intent(this, Detalle_tareas_finalizadas.class);
                intent.putExtra("data", data);
                intent.putExtra("trabajos", "diarios");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            PendingIntent pIntent = PendingIntent.getActivity(this, data.getId().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            return new Notification.Builder(getApplicationContext(), channelId)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pIntent)
                    .setSmallIcon(R.drawable.icon_task)
                    .setAutoCancel(true);
        }
        return null;
    }

    private Notification.Builder createNotificationWithoutChannel(String title, String message, data_task data) {
        String estado = data.getEstado();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Intent intent=new Intent(this,Detalle_tareas.class);
        if (!estado.equals("Finalizado")) {
            intent = new Intent(this, Detalle_tareas.class);
            intent.putExtra("data", data);
            intent.putExtra("trabajos", "diarios");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent = new Intent(this, Detalle_tareas_finalizadas.class);
            intent.putExtra("data", data);
            intent.putExtra("trabajos", "diarios");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent pIntent = PendingIntent.getActivity(this, data.getId().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.icon_task)
                .setSound(defaultSoundUri)
                .setAutoCancel(true);

    }
}