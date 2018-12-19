package com.example.amere.aplicacion_mantenimiento_hcsf;


import android.app.Notification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
public class NotificationService extends FirebaseMessagingService {

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            administrador_notificaciones adm = new administrador_notificaciones(NotificationService.this);
            Notification.Builder nb = adm.createNotification(remoteMessage.getFrom(), "Hola", false);
            adm.getManager().notify(1, nb.build());
        }
    }
}
